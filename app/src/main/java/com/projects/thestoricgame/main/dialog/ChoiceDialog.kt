package com.projects.thestoricgame.main.dialog

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.projects.thestoricgame.databinding.ChoiceDialogBinding
import com.projects.thestoricgame.model.MessageItem
import com.projects.thestoricgame.utils.extensions.setWidthPercent

class ChoiceDialog(
    private val item: MessageItem,
    private val saveData: (jumpIndex: Int) -> Unit
) : DialogFragment() {
    private lateinit var _binding: ChoiceDialogBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChoiceDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(95)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val radioButtonMap = linkedMapOf<Int, String>()

            tvQuestion.text = item.message
            item.choices?.let {
                for ((text, value) in it) {
                    val radioButton = RadioButton(requireContext()).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        setText(text)
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                        id = View.generateViewId()
                    }
                    radioButtonMap[radioButton.id] = text
                    groupRadio.addView(radioButton)
                }
            }

            btnSave.setOnClickListener {
                val selectedRadioButtonId = groupRadio.checkedRadioButtonId
                if (selectedRadioButtonId != -1) {
                    val selectedKey = radioButtonMap[selectedRadioButtonId]
                    val selectedValue = item.choices?.get(selectedKey)
                    saveData.invoke(selectedValue ?: item.choices?.values?.first() ?: 0)
                    dialog?.dismiss()
                } else {
                    Toast.makeText(requireContext(), "No option selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

}