package com.vanh.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ScoreViewModelFactory(private val finalScore:Int):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            Log.i("ScoreViewModelFactory","created with value $finalScore")
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }

}