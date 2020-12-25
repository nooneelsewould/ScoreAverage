package com.example.scoreaverage

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScoreDao {
    @Insert
    fun insertScore(vararg score: Score?)

    @Update
    fun updateScore(vararg score: Score?)

    @Query("select * from SCORE")
    fun loadAllNotes(): List<Score>

    @Delete
    fun deleteScore(vararg score: Score?)

    @Query("SELECT * FROM SCORE ORDER BY ID ASC") //升序查询
    fun getAllScoreLive(): LiveData<List<Score>>
}