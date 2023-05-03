package com.example.skillbox_hw_quiz.ui.main.fragments

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.R
import com.example.skillbox_hw_quiz.quiz.QuizStorage
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    val calendar = Calendar.getInstance()

    private val pattern = "dd MMMM yyyy"
    private var dateFormat = SimpleDateFormat(pattern, Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btnStart = view.findViewById<Button>(R.id.btnStart)
        val editViewDate = view.findViewById<EditText>(R.id.editViewDate)

        editViewDate.setOnClickListener {
            val dateDialog = MaterialDatePicker.Builder.datePicker()
                .setTitleText(resources.getString(R.string.choose_the_date_dialog_title))
                .build()

            dateDialog.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
                Snackbar.make(editViewDate, dateFormat.format(calendar.time), Snackbar.LENGTH_SHORT)
                    .show()

                editViewDate.setText(dateFormat.format(calendar.time))
            }
            dateDialog.show(parentFragmentManager, "DatePicker")
        }

        btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_quizFragment)
        }
    }

}