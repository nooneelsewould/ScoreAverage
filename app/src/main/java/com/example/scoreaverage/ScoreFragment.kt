package com.example.scoreaverage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoreaverage.MyAdapter.OnItemChangedListener
import kotlinx.android.synthetic.main.fragment_score.*


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
    private lateinit var allScore: List<Score>

    var scoreAverageResult: Double = 0.0

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

//        scoreAverage.text = scoreAverageResult.toString()
        myAdapter = context?.let { MyAdapter(it) }!!
        myAdapter.setOnItemChangedListener(object : OnItemChangedListener {
            override fun onItemChanged() {
//                var scoreMultiplyCredit: Double = 0.0
//                var creditSum: Double = 0.0
//                for (item: Score in scoreViewModel.getAllScoreLive().value!!) {
//                    scoreMultiplyCredit += item.getScore().toDouble() * item.getCredit().toDouble()
//                    creditSum += item.getCredit().toDouble()
//                }
//                scoreAverageResult = scoreMultiplyCredit / creditSum
//                scoreAverage.text = scoreAverageResult.toString()
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.adapter = myAdapter

        scoreViewModel.getAllScoreLive().observe(viewLifecycleOwner,
            { t -> //设置数据源
                if (t != null) {
                    myAdapter.setAllScore(t)
                    myAdapter.notifyDataSetChanged()
                }
            })


        floatingActionButton.setOnClickListener(View.OnClickListener {
            val navController: NavController = Navigation.findNavController(it)
            navController.navigate(R.id.action_scoreFragment_to_addFragment)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        scoreViewModel = ViewModelProvider(this)[ScoreViewModel::class.java]
//        var scoreMultiplyCredit = 0.0
//        var creditSum = 0.0
//        for (item: Score in scoreViewModel.getAllScoreLive().value!!) {
//            scoreMultiplyCredit += item.getScore().toDouble() * item.getCredit().toDouble()
//            creditSum += item.getCredit().toDouble()
//        }
//        scoreAverageResult = scoreMultiplyCredit / creditSum

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
