package com.example.scoreaverage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ScoreViewModel(application: Application) : AndroidViewModel(application) {

    private var scoreRepository: ScoreRepository = ScoreRepository(application)

    fun getAllScoreLive(): LiveData<List<Score>> {
        return scoreRepository.getAllScoreLive()
    }

    fun insertScore(vararg params: Score?) {
        scoreRepository.insertScore(*params)
    }

    fun deleteScore(vararg params: Score?){
        scoreRepository.deleteScore(*params)
    }

}