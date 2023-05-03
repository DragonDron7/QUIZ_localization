package com.example.skillbox_hw_quiz.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.view.animation.AnimationUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.databinding.QuizFragmentBinding
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import java.util.*


class QuizFragment : Fragment() {

    private var _binding: QuizFragmentBinding? = null
    private val binding get() = _binding!!
    private var answers: MutableList<Int>? = null

    private val questionsList =
        if (Locale.getDefault().country.toString().uppercase() == "RU") QuizStorage.getQuiz(
            QuizStorage.Locale.Ru
        )
        else QuizStorage.getQuiz(
            QuizStorage.Locale.En
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = QuizFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var radioGroupCount = 0 //кол-во radioGroup

        //динамический вывод View с вопросами и предполагаемыми ответами--------------------------

        questionsList.questions.forEach { //проходим по списку вопросов

            val questionTextView = TextView(context, null, 0, R.style.customText)
            questionTextView.text = it.question //помещаем вопрос в создаваемое поле
            questionTextView.textSize = 18F
            questionTextView.setPadding(5, 15, 5, 5)

            // Create RadioGroup Dynamically
            val radioGroup = RadioGroup(context)
            radioGroup.setPadding(10, 5, 5, 5)
            radioGroup.setBackgroundResource(R.drawable.border)
            radioGroup.id = radioGroupCount + 1000
            radioGroupCount++

            it.answers.forEachIndexed { index, text -> //проходим по списку предполагаемых ответов
                val radioBtn = RadioButton(context) //создаём RadioButton
                radioBtn.id = index
                radioBtn.text = text
                radioBtn.textSize = 16F

                radioGroup.addView(radioBtn)
            }

            _binding!!.LinearLayoutQuiz.addView(questionTextView)
            _binding!!.LinearLayoutQuiz.addView(radioGroup)

            //анимация--------------------
            radioGroup.alpha = 0.3f
            radioGroup.scaleX = 0.5f
            radioGroup.scaleY = 0.5f
            radioGroup.animate().apply {
                duration = 500
                scaleX(1f)
                scaleY(1f)
                alpha(1f)
                startDelay = 700
            }.start()
            //---------------------------

            answers = MutableList(radioGroupCount) { -1 }//список по кол-ву radioGroup

            //слушатель для созданной radioGroup(s)
            radioGroup.setOnCheckedChangeListener { group, radioBtnId ->
                // если radioGroup существует, и в ней есть кнопки
                if (group != null && radioBtnId > -1) {
                    answers!![group.id - 1000] =
                        radioBtnId //записываем id кнопки в нужный index списка
                }
            }
        }
        //-------------------------------слушатель кнопки НАЗАД----------------------------------
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_mainFragment)
        }
        //-------------------------------слушатель кнопки ОТПРАВИТЬ------------------------------
        binding.btnSend.setOnClickListener {
            if (answers?.contains(-1) == true) Toast.makeText(
                activity, R.string.text_toast_quiz_no_answer, Toast.LENGTH_SHORT
            ).show()
            else {
                val bundle = Bundle() //переменная для набора данных
                val feedback = answers?.let { answer ->
                    QuizStorage.answer(questionsList, answer)
                }
                bundle.putString("Answers", feedback)
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}