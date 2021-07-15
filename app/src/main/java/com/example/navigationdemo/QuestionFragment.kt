package com.example.navigationdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.navigationdemo.databinding.FragmentQuestionBinding


class QuestionFragment : Fragment() {

    //variable declaration
    lateinit var binding: FragmentQuestionBinding
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    var questionIndex = 0
    var score :Int =0

    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "Which Order have been implemented by Malaysia government to prevent COVID-19?",
            answers = listOf("Movement Control Order", "Multiple Choice Order", "More Coffee Order " )
        ),
        Question(
            text = "What is FMCO?",
            answers = listOf("Full Movement Control Oder",  "Fun Movement Control Oder", "Forever Movement Control Oder")
        // already create as a list ( refer to radio button a b c )
        )
    )


    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()

        binding.textView.text = currentQuestion.text
        binding.optA.text = answers[0]
        binding.optB.text = answers[1]
        binding.optC.text = answers[2]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_question, container, false)

        setQuestion()

        binding.btnNext.setOnClickListener(){
            val checkedId = binding.radioGroup.checkedRadioButtonId

            if (checkedId != -1) { // -1 user didnt choose anything
                var answerIndex = 0



                when (checkedId) {
                    R.id.optA -> answerIndex = 0
                    R.id.optB -> answerIndex = 1
                    R.id.optC -> answerIndex = 2
                }

                if (answers[answerIndex] == currentQuestion.answers[0]) { // user input with the answer is the same then will get one score
                    score += 1
                }

                if (questionIndex < 1) {
                    questionIndex += 1 // add the question by change this index
                    binding.radioGroup.clearCheck() // clear all the button for the second and following question
                    setQuestion()

                } else {

                    Navigation.findNavController(it).navigate(R.id.action_questionFragment_to_thankYouFragment)
                }
            }else{
                Toast.makeText(context, "please select answer", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }


}