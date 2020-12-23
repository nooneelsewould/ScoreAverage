package com.example.scoreaverage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Score(course: String, score: String, credit: String, remark: String) {

    @PrimaryKey(autoGenerate = true)
    private var id = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    @ColumnInfo(name = "course")
    private var course: String

    fun getCourse(): String {
        return course
    }

    fun setCourse(course: String) {
        this.course = course
    }


    @ColumnInfo(name = "score")
    private var score: String

    fun getScore(): String {
        return score
    }

    fun setScore(course: String) {
        this.score = score
    }

    @ColumnInfo(name = "credit")
    private var credit: String

    fun getCredit(): String {
        return credit
    }

    fun setCredit(course: String) {
        this.credit = credit
    }

    @ColumnInfo(name = "remark")
    private var remark: String

    fun getRemark(): String {
        return remark
    }

    fun setRemark(remark: String) {
        this.remark = remark
    }

    init {
        this.course = course
        this.score = score
        this.credit = credit
        this.remark = remark
    }
}