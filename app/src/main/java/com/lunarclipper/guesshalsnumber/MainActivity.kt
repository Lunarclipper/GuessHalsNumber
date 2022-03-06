package com.lunarclipper.guesshalsnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    // Setup player interaction button listener
        val player_action: Button = findViewById(R.id.playerAction)
        player_action.setOnClickListener {
            doit()
        } //END player_action.setOnClickListener

    /* Click listener not needed for radio buttons: Action taken by checked button only when
    * playerAction is tapped. Changing which button is checked when one is tapped is handled
    * by Android. */
    } //END override fun onCreate(savedInstanceState: Bundle?)
    val player = User()
    val aiHAL = HAL9000()

    fun doit() {
        /* TODO: Setup actions to be taken depending on the state of the user interaction button */
        /* determine state of game by player_action.text
         * @string/set: -set maxRange by .text.toInt() of checked button (see Tip Time)
         *              -HAL9000.playerNumber = (1..maxRange).random()
         *              -make HALsNumberRange invisible
         *              -radioGroupLabel.text = getString(R.String.hal_range_set, maxRange)
         *                     -make playersGuess visible
         *
         * @string/guess: -User.playerNumber = playersGuess.text.toInt()
         *                -Check User.playerNumber against HAL9000.playerNumber
         *                     -User > Hal -> HALsVoice.text = @string/lower
         *                     -User < Hal -> HALsVoice.text = @string/higher
         *                     -User == Hal -> {HALsVoice.text = @string/correct
         *                                      playerAction.text = @string/play_again}
         * @string/play_again: -make HALsNumberRange visible
         *                     -player_action.text = @string/set
         *                     -radioGroupLabel.text = @string/hal_picks_between_1_and
         *                     -HALsVoice.text = @string/ready
         *                     -make playersGuess invisible */
    }//END fun doit()
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