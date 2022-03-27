package com.lunarclipper.guesshalsnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

//Forced portrait app orientation with entry in AndroidManifest.xml
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setup player interaction button listener
        val playerAction: Button = findViewById(R.id.playerAction)
        var score = 0
        playerAction.setOnClickListener {
            score = doIt(playerAction, score)
        } //END player_action.setOnClickListener

        /* Click listener not needed for radio buttons: Action taken by checked button only when
        * playerAction is tapped. Changing which button is checked when one is tapped is handled
        * by Android. */
    } //END override fun onCreate(savedInstanceState: Bundle?)

    private val player = User()
    private val aiHAL = HAL9000()

    private fun doIt(playerAction: Button, oldScore: Int): Int {
        /* check to see if click event listener is working on button.
        playerAction.text ="¡GANO!"
        */
        //Set UI handler variables
        val playerGuess: EditText = findViewById(R.id.playersGuess)
        val halsVoice: TextView = findViewById(R.id.HALsVoice)
        val pickRange: TextView = findViewById(R.id.radioGroupLabel)
        val halsNumberRange: RadioGroup = findViewById(R.id.HALsNumberRange)
        val selectedRange: RadioButton = findViewById(halsNumberRange.checkedRadioButtonId)
        val playerScore: TextView = findViewById(R.id.numberOfGuesses)
        //Set the highest number HAL can pick
        val maxNumber: Int = selectedRange.text.toString().toInt()
        //Get player's guess
        val guess = playerGuess.text.toString().trim()
        var score = oldScore

        //determine state of game by player_action.text
        when (playerAction.text.toString()) {
            //Initialize game
            getString(R.string.set) -> {
                //Step 1: Start counting number of player guesses
                playerScore.text = "0"
                //Step 2: Make EditText for player's guess visible
                playerGuess.visibility = View.VISIBLE
                //Step 3: Set Radio button group label to display number range to player
                pickRange.text = getString(R.string.hal_range_set, maxNumber.toString())
                //Step 4: Make range selection buttons invisible
                halsNumberRange.visibility = View.INVISIBLE
                //Step 5: Change game state to player guess mode
                playerAction.text = getString(R.string.guess)

                aiHAL.playerNumber = (1..maxNumber).random()
                return score
            } //END R.string.set

            //Play game
            getString(R.string.guess) -> {
                //Do nothing if guess is empty or NULL
                if (guess.isEmpty()) return score
                //If guess is not empty or null set player.playerNumber
                else player.playerNumber = guess.toInt()

                //Check player.playerNumber against aiHAL.playerNumber
                when {
                    aiHAL.playerNumber < player.playerNumber -> {
                        //update hint
                        halsVoice.text = getString(R.string.lower)
                        //Clear last guess
                        playerGuess.setText(getString(R.string.clearEntry))
                        //update and display score
                        score = updateScore(score, playerScore)
                    } //END aiHAL.playerNumber < player.playerNumber
                    aiHAL.playerNumber > player.playerNumber -> {
                        //update hint
                        halsVoice.text = getString(R.string.higher)
                        //Clear last guess
                        playerGuess.setText(getString(R.string.clearEntry))
                        //update and display score
                        score = updateScore(score, playerScore)
                    } //END aiHAL.playerNumber > player.playerNumber
                    else -> {
                        halsVoice.text = getString(R.string.correct)
                        playerAction.text = getString(R.string.play_again)
                        //update and display score
                        score = updateScore(score, playerScore)
                    } //END else
                }// END when
                return score
            } //END R.string.guess

            //Reset game to initialize state
            getString(R.string.play_again) -> {
                //Step 1: Reset player score
                score = 0
                //Step 2: Make EditText for player's guess invisible
                playerGuess.visibility = View.INVISIBLE
                //Step 3: Re-set Radio button group label to display starting message
                pickRange.text = getString(R.string.hal_picks_between_1_and)
                //Step 4: Make range selection buttons visible
                halsNumberRange.visibility = View.VISIBLE
                //Step 5: Change game state to initial state
                playerAction.text = getString(R.string.set)

                //display a blank space for number of guesses
                playerScore.text = getString(R.string.clearEntry)
                //Clear last guess
                playerGuess.setText(getString(R.string.clearEntry))
                //Change hint to say, "Ready"
                halsVoice.text = getString(R.string.ready)
                return score
            } //END R.string.play_again
        } //END when (playerAction)
        return score
    }//END fun doIt()

    private fun updateScore(score: Int, playerScore: TextView): Int {
        val newScore = score+1
        playerScore.text = newScore.toString()
        return newScore
    } //END private fun updateScore(score: Int, playerScore: TextView): Int
} //END class MainActivity : AppCompatActivity()

//Base class for both App and human players
abstract class Player {
    abstract var playerNumber: Int
} //END Player

//Human player class
class User : Player() {
    override var playerNumber = 0 //currently set here for a pre-game value
} //END class User: Player()

/* The following class name is written HAL9000, instead of Hal9000, purely for the amusement of
 * author for its reference to HAL9000 from 2001: A Space Odyssey
 */
//App player class
class HAL9000 : Player() {
    override var playerNumber = 0 //currently set here for a pre-game value
} //END class HAL9000: Player