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
        playerAction.setOnClickListener {
            doIt(playerAction)
        } //END player_action.setOnClickListener

    /* Click listener not needed for radio buttons: Action taken by checked button only when
    * playerAction is tapped. Changing which button is checked when one is tapped is handled
    * by Android. */
    } //END override fun onCreate(savedInstanceState: Bundle?)
    val player = User()
    val aiHAL = HAL9000()

    private fun doIt(playerAction: Button) {
        /* check to see if click event listener is working on button.
        playerAction.text ="¡GANO!"
        */
    //Set UI handler variables
        val halsNumberRange: RadioGroup = findViewById(R.id.HALsNumberRange)
        val selectedRange: RadioButton = findViewById(halsNumberRange.checkedRadioButtonId)
        val pickRange: TextView = findViewById(R.id.radioGroupLabel)
        val playerGuess: EditText = findViewById(R.id.playersGuess)
        val playerScore: TextView = findViewById(R.id.numberOfGuesses)
    //Set the highest numer HAL can pick
        val maxNumber: Int = selectedRange.text.toString().toInt()

        //determine state of game by player_action.text
        when (playerAction.text.toString()) {
            getString(R.string.set) -> {
                //Start counting number of player guesses
                playerScore.text = "0"
                //Make EditText for player's guess visible
                playerGuess.visibility = View.VISIBLE
                //Display number range to player
                pickRange.text = getString(R.string.hal_range_set, maxNumber.toString())
                //Make range selection buttons invisible
                halsNumberRange.visibility = View.INVISIBLE
                //Change game state to player guess mode
                playerAction.text = getString(R.string.guess)
            } //END R.string.set

            getString(R.string.guess) -> {
                playerAction.text = getString(R.string.play_again)
            } //END R.string.guess

            getString(R.string.play_again) -> {
                //display a blank space for number of guesses
                playerScore.text =" "
                //Make EditText for player's guess invisible
                playerGuess.visibility = View.INVISIBLE
                //Re-set Radio button group label
                pickRange.text = getString(R.string.hal_picks_between_1_and)
                //Make range selection buttons visible
                halsNumberRange.visibility = View.VISIBLE
                //Change game state to initial state
                playerAction.text = getString(R.string.set)
            } //END R.string.play_again
        //@string/set: -HAL9000.playerNumber = (1..maxRange).random()
        //-make playersGuess visible
        } //END when (playerAction)
        /* TODO: Setup actions to be taken depending on the state of the user interaction button
         * determine state of game by player_action.text

         *
         * @string/guess: -User.playerNumber = playersGuess.text.toInt()
         *                -Check User.playerNumber against HAL9000.playerNumber
         *                     -User > Hal -> HALsVoice.text = @string/lower
         *                     -User < Hal -> HALsVoice.text = @string/higher
         *                     -User == Hal -> {HALsVoice.text = @string/correct
         *                                      playerAction.text = @string/play_again}
         * @string/play_again: -player_action.text = @string/set
         *                     -radioGroupLabel.text = @string/hal_picks_between_1_and
         *                     -HALsVoice.text = @string/ready
         *                     -make playersGuess invisible */
    }//END fun doIt()
} //END class MainActivity : AppCompatActivity()

//Base class for both App and human players
abstract class Player {
    abstract var playerNumber: Int
} //END Player

//Human player class
class User: Player() {
    /* TODO: Check to see if there is a better way to make this value in User and HAL9000
    *  buildable, without setting it to a value outside the acceptable range initially */
    override var playerNumber = 0 //currently set here for a pre-game value
} //END class User: Player()

/* The following class name is written HAL9000, instead of Hal9000, purely for the amusement of
 * author for its reference to HAL9000 from 2001: A Space Odyssey
 */
//App player class
class HAL9000: Player() {
    override var playerNumber = 0 //currently set here for a pre-game value
} //END class HAL9000: Player