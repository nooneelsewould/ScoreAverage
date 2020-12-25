package com.example.scoreaverage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_score.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScoreFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var scoreViewModel: ScoreViewModel
    lateinit var myAdapter: MyAdapter
    private var allScore: List<Score> = ArrayList()

    var scoreAverageResult = 0.0

    private fun setAllScoreHere(allScore: List<Score>) {
        this.allScore = allScore
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        scoreViewModel = ViewModelProvider(this)[ScoreViewModel::class.java]

        scoreAverage.text = "查看"

        scoreAverage.setOnClickListener(View.OnClickListener {
            var scoreMultiplyCredit = 0.0
            var creditSum = 0.0
            for (item: Score in allScore) {
                scoreMultiplyCredit += item.getScore().toDouble() * item.getCredit().toDouble()
                creditSum += item.getCredit().toDouble()
            }
            scoreAverageResult = scoreMultiplyCredit / creditSum
            scoreAverage.text = scoreAverageResult.toString()
        })

        myAdapter = context?.let { MyAdapter(it) }!!
        myAdapter.setOnItemChangedListener(object : MyAdapter.OnItemChangedListener {
            override fun onItemChanged() {
                var scoreMultiplyCredit = 0.0
                var creditSum = 0.0
                for (item: Score in allScore) {
                    scoreMultiplyCredit += item.getScore().toDouble() * item.getCredit().toDouble()
                    creditSum += item.getCredit().toDouble()
                }
                scoreAverageResult = scoreMultiplyCredit / creditSum
                scoreAverage.text = scoreAverageResult.toString()
                scoreAverage.text = "查看"
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.adapter = myAdapter

        scoreViewModel.getAllScoreLive().observe(viewLifecycleOwner,
            { t -> //设置数据源
                if (t != null) {
                    myAdapter.setAllScore(t)
                    setAllScoreHere(t)
                    myAdapter.notifyDataSetChanged()
                }
            })


        floatingActionButton.setOnClickListener(View.OnClickListener {
            val navController: NavController = Navigation.findNavController(it)
            navController.navigate(R.id.action_scoreFragment_to_addFragment)
        })


        val helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                var dragFlag = 0
                dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlag, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //得到当拖拽的viewHolder的Position
                val fromPosition = viewHolder.adapterPosition
                //拿到当前拖拽到的item的viewHolder
                val toPosition = target.adapterPosition

                val scoreDao = ScoreDatabase.getDatabase(context!!)?.getScoreDao()

                if (fromPosition < toPosition) {
                    for (i in fromPosition until toPosition) {
                        Collections.swap(myAdapter.getAllScore(), i, i + 1)
                    }
                } else {
                    for (i in fromPosition downTo toPosition + 1) {
                        Collections.swap(myAdapter.getAllScore(), i, i - 1)
                    }
                }

                thread {
                    val tempList = scoreDao?.loadAllNotes()?.toMutableList()
                    if (tempList != null) {
                        for (i in 0 until tempList.size) {
                            tempList[i].setCourse(allScore[i].getCourse())
                            tempList[i]?.setScore(allScore[i].getScore())
                            tempList[i]?.setCredit(allScore[i].getCourse())
                            tempList[i]?.setRemark(allScore[i].getRemark())
                            scoreDao?.updateScore(tempList?.get(i))
                        }
                    }
                }

                myAdapter.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //侧滑删除可以使用；
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }
        })
        helper.attachToRecyclerView(recyclerView)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScoreFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
