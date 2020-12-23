package com.example.scoreaverage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class ScoreDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: ScoreDatabase? = null
        @Synchronized//如果有多个客户端  且同时instance时保证不会有碰撞 只有一个instance生成
        open fun getDatabase(context: Context): ScoreDatabase? {//静态类的静态方法写open
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ScoreDatabase::class.java,
                    "score_database"
                ).build()
            }
            return INSTANCE
        }
    }
    abstract fun getScoreDao(): ScoreDao
}