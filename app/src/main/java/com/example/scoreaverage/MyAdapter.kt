package com.example.scoreaverage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_card.view.*
import java.util.*

class MyAdapter(var context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var allScore: List<Score> = ArrayList()
    private lateinit var scoreViewModel: ScoreViewModel
    lateinit var myOnItemChangedListener: OnItemChangedListener

    fun setAllScore(allScore: List<Score>) {
        this.allScore = allScore
    }

    //内部类
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var course: TextView = itemView.course
        var score: TextView = itemView.score
        var credit: TextView = itemView.credit
        var remark: TextView = itemView.remark
        var remove: ImageButton = itemView.removeBtn

        init {
            course = itemView.course
            score = itemView.score
            credit = itemView.credit
            remark = itemView.remark
            remove = itemView.removeBtn
        }
    }

    override fun getItemCount(): Int {
        return allScore.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView: View
        itemView = layoutInflater.inflate(R.layout.cell_card, parent, false)

        scoreViewModel = ViewModelProvider((context as FragmentActivity))[ScoreViewModel::class.java]
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val score = allScore[position]
        holder.course.text = score.getCourse()
        holder.score.text = score.getScore()
        holder.credit.text = score.getCredit()
        holder.remark.text = score.getRemark()

        holder.remove.setOnClickListener {
            scoreViewModel.deleteScore(allScore[holder.adapterPosition])
            notifyDataSetChanged()
            myOnItemChangedListener.onItemChanged()
        }
    }

    interface OnItemChangedListener {
        fun onItemChanged()
    }

    public fun setOnItemChangedListener(onItemChangedListener: OnItemChangedListener) {
        myOnItemChangedListener = onItemChangedListener
    }

}