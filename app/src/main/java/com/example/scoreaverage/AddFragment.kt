package com.example.scoreaverage

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var scoreViewModel: ScoreViewModel

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
        button.isEnabled = false

        val textWatcher = (object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val course: String = courseEdit.text.toString().trim()
                val scoreHere: String = scoreEdit.text.toString().trim()
                val credit: String = creditEdit.text.toString().trim()
                val remark: String = remarkEdit.text.toString().trim()
                button.isEnabled = course != "" && scoreHere != "" && credit != "" && remark != ""
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        courseEdit.addTextChangedListener(textWatcher)
        scoreEdit.addTextChangedListener(textWatcher)
        creditEdit.addTextChangedListener(textWatcher)
        remarkEdit.addTextChangedListener(textWatcher)

        button.setOnClickListener(View.OnClickListener {
            val course: String = courseEdit.text.toString().trim()
            val scoreHere: String = scoreEdit.text.toString().trim()
            val credit: String = creditEdit.text.toString().trim()
            val remark: String = remarkEdit.text.toString().trim()
            val score = Score(course, scoreHere, credit, remark)
            scoreViewModel.insertScore(score)
            view?.let { it1 -> Navigation.findNavController(it1) }?.navigateUp()
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}