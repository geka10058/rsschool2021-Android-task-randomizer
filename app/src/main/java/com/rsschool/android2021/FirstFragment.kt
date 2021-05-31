package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var min: EditText? = null
    private var max: EditText? = null
    private var listener: ActionPerformedListener? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        min = view.findViewById(R.id.min_value)
        max = view.findViewById(R.id.max_value)


        generateButton?.setOnClickListener {
            when {
                min != null && min?.text?.isEmpty() == true && max != null && max?.text?.isEmpty() == true -> Toast.makeText(
                    activity,
                    "Оба поля пусты! Введите данные!",
                    Toast.LENGTH_LONG
                ).show()
                min != null && min?.text?.isEmpty() == true -> Toast.makeText(
                    activity,
                    "В поле MIN неверные данные!",
                    Toast.LENGTH_LONG
                ).show()
                max != null && max?.text?.isEmpty() == true -> Toast.makeText(
                    activity,
                    "В поле MAX неверные данные!",
                    Toast.LENGTH_LONG
                ).show()
                min != null && min?.text?.isEmpty() == true && max != null && max?.text?.isEmpty() == true -> Toast.makeText(
                    activity,
                    "В полях неверные данные!",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    val minVal = min?.text.toString().toIntOrNull()
                    val maxVal = max?.text.toString().toIntOrNull()
                    Log.d("FMJBHG", "$minVal $maxVal")
                    if (minVal == null || maxVal == null) {
                        Toast.makeText(
                            activity,
                            "Превышен диапазон значений!",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("2 POPITCA", "$minVal $maxVal")
                    } else {
                        if (minVal < maxVal) listener?.onActionPerformed(minVal, maxVal)
                        else Toast.makeText(
                            activity,
                            "Значение Min >= Max! Так нельзя!!",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                }
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface ActionPerformedListener {
        fun onActionPerformed(min: Int, max: Int)
    }
}