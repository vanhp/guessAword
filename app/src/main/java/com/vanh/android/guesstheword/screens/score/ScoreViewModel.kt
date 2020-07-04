package com.vanh.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore:Int): ViewModel(){

    private val _yourScore = MutableLiveData<Int>()
    val yourScore: LiveData<Int>
        get() = _yourScore

    private val _playAgain = MutableLiveData<Boolean>()
    val playAgain:LiveData<Boolean>
        get() = _playAgain


    init {
        Log.i("ScoreViewModel","created")
        _yourScore.value = finalScore
        _playAgain.value = false
    }
    fun onPlayAgain() {
        _playAgain.value = true
    }
    fun onPlayAgainDone(){
        _playAgain.value = true
    }
}