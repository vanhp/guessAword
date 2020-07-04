/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vanh.android.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.NavHostFragment.findNavController
import com.vanh.android.guesstheword.R
import com.vanh.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel

        viewModel.Buzzing.observe(viewLifecycleOwner, Observer{
            newBuzz -> if(newBuzz == BuzzType.GAME_OVER){
                                gameFinished()
                                viewModel.onBuzzCompleted()
                        }
        })

//
//        binding.correctButton.setOnClickListener {
//            viewModel.onCorrect()
//           // viewModel.stopTimer()
//            viewModel.reSetTimer()
//        }

//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//        }

//        viewModel.score.observe(this, Observer {
//            newScore -> binding.scoreText.text = newScore.toString() })
//        viewModel.word.observe(this, Observer {
//            newWord -> binding.wordText.text = newWord.toString() })
//        viewModel.tick.observe(this, Observer {
//            newTick -> binding.timerText.text = DateUtils.formatElapsedTime(newTick)
//        })

        viewModel.eventGameFinished.observe(this, Observer {
            gameFinish -> if(gameFinish) {
                viewModel.stopTimer()
                gameFinished()
               // viewModel.onGameFinishCompleted()
            }
        })
        return binding.root
      //  Log.i("onCreateView","vieModel created")
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {

        val action = GameFragmentDirections.actionGameDestinationToScoreDestination(viewModel.score.value?:0)
        findNavController(this).navigate(action)

       // val action = GameFragmentDirections.actionGameToScore(viewModel.score.value?:0)
     //   findNavController(this).navigate(action)
//        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value?:0)
//        findNavController(this).navigate(action)

       // viewModel.stopTimer()
        //  viewModel.onGameFinishCompleted()
//        val action = GameFragmentDirections.actionGameDestinationToTitleDestination()
//        findNavController(this).navigate(action)

    }
    private fun buzz(pattern: LongArray){
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer?.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                buzzer.vibrate(VibrationEffect.createWaveform(pattern,-1))
            }else buzzer.vibrate(pattern,-1)
        }

    }



}
