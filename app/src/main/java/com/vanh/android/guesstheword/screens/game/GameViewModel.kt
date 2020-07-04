package com.vanh.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.vanh.android.guesstheword.screens.game.NO_BUZZ_PATTERN as NO_BUZZ_PATTERN1

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

enum class BuzzType(val pattern:LongArray) {
    CORRECT(CORRECT_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN1)
}
class GameViewModel:ViewModel(){

    private val _buzzType = MutableLiveData<BuzzType>()
    val Buzzing:LiveData<BuzzType>
        get() = _buzzType

    // The current word
    private val _word = MutableLiveData<String>()
    val word:LiveData<String>
        get() = _word


    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished:LiveData<Boolean>
            get() = _eventGameFinished

    val timer:CountDownTimer
    private var _tick = MutableLiveData<Long>()
    val tick:LiveData<Long>
        get() = _tick

    val currentTickString = Transformations.map(tick)
                    {time -> DateUtils.formatElapsedTime(time)}

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel","GameViewModel initial created")
        resetList()
        nextWord()
        _score.value = 0
        _word.value = ""
        _eventGameFinished.value = false
        _buzzType.value = BuzzType.NO_BUZZ

        timer = object : CountDownTimer(COUNTDOWN_TIMER, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _tick.value = millisUntilFinished / ONE_SECOND

            }
            override fun onFinish() {
                _eventGameFinished.value = true
            }
        }
        timer.start()
    }

    fun onBuzzCompleted(){
      //  _buzzType.value =
    }


    fun stopTimer(){
        timer.cancel()
    }
    fun reSetTimer(){
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel","onCleared destroyed")
    }

    /**
     * Moves to the next word in the list
     */
     fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) resetList() //_eventGameFinished.value = true
        else _word.value = wordList.removeAt(0)
    }

     fun onSkip() {
       // score--
         _score.value = (_score.value)?.minus(1)
        nextWord()
    }

     fun onCorrect() {
//        score++
         _score.value = (_score.value)?.plus(1)
         nextWord()
         reSetTimer()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }
    fun onGameFinishCompleted(){
        _eventGameFinished.value = true
    }
    companion object{
        private const val DONE = 0
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIMER = 10000L
    }
}