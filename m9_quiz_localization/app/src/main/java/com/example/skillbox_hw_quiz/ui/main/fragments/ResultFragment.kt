package com.example.skillbox_hw_quiz.ui.main.fragments

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.example.skillbox_hw_quiz.MainActivity
import com.example.skillbox_hw_quiz.R

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewResult = view.findViewById<TextView>(R.id.textViewResult)
        val btnRestart = view.findViewById<Button>(R.id.btnRestart)
        val closeLottie = view.findViewById<LottieAnimationView>(R.id.animation_Lottie)

        textViewResult.text = arguments?.getString("Answers")

        (AnimatorInflater.loadAnimator(context, R.animator.animation_x) as AnimatorSet).apply {
            setTarget(textViewResult)
        }.start()

        closeLottie.setOnClickListener {
            activity?.finish() //выход из приложения
        }

        btnRestart.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
        }
    }

}