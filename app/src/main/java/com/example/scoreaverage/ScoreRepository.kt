package com.example.scoreaverage

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ScoreRepository(context: Context) {
    private var allScoreLive: LiveData<List<Score>>
    private var scoreDao: ScoreDao
    var scoreDatabase: ScoreDatabase = ScoreDatabase.getDatabase(context.applicationContext)!!

    init {
        scoreDao = scoreDatabase.getScoreDao()
        allScoreLive = scoreDao.getAllScoreLive()
    }

    //写接口调用这些
    fun insertScore(vararg params: Score?) {
        InsertAsyncTask(scoreDao).execute(*params)
    }

    fun deleteScore(vararg params: Score?) {
        DeleteAsyncTask(scoreDao).execute(*params)
    }

    fun updateScore(vararg params: Score?) {
        UpdateAsyncTask(scoreDao).execute(*params)
    }

    fun getAllScoreLive(): LiveData<List<Score>> {
        return allScoreLive
    }

    internal class InsertAsyncTask(private val scoreDao: ScoreDao) : AsyncTask<Score?, Unit, Unit>() {
        //在后台工作
        override fun doInBackground(vararg params: Score?) {
            scoreDao.insertScore(*params)
        }
    }

    internal class DeleteAsyncTask(private val scoreDao: ScoreDao) : AsyncTask<Score?, Unit, Unit>() {
        //在后台工作
        override fun doInBackground(vararg params: Score?) {
            scoreDao.deleteScore(*params)
        }
    }

    internal class UpdateAsyncTask(private val scoreDao: ScoreDao) : AsyncTask<Score?, Unit, Unit>() {
        //在后台工作
        override fun doInBackground(vararg params: Score?) {
            scoreDao.updateScore(*params)
        }
    }
}