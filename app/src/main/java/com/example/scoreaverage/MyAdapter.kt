package com.example.scoreaverage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_card.view.*

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var allScore: List<Score> = ArrayList()

    fun setAllScore(allScore: List<Score>) {
        this.allScore = allScore
    }

    //内部类
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var course: TextView = itemView.course
        var score: TextView = itemView.score
        var credit: TextView = itemView.credit
        var remark: TextView = itemView.remark

        init {
            course = itemView.course
            score = itemView.score
            credit = itemView.credit
            remark = itemView.remark
        }
    }

    override fun getItemCount(): Int {
        return allScore.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView: View
        itemView = layoutInflater.inflate(R.layout.cell_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val score = allScore[position]
        holder.course.text = score.getCourse()
        holder.score.text = score.getScore()
        holder.credit.text = score.getCredit()
        holder.remark.text = score.getRemark()
    }

}