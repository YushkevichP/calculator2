
package com.example.homework1calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework1calculator.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = requireNotNull(_binding) { "Binding is empty" }

    private val operationHistory = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCalculatorBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtons()
        binding.resultPlace.setOnClickListener {
            pushFragment(operationHistory)

        }
    }

    private fun initButtons() = with(binding) {

        resultPlace.text = ""
        workPlace.text = "0"
        buttonOne.setOnClickListener { makeOperation("1") }
        buttonTwo.setOnClickListener { makeOperation("2") }
        buttonThree.setOnClickListener { makeOperation("3") }
        buttonFour.setOnClickListener { makeOperation("4") }
        buttonFive.setOnClickListener { makeOperation("5") }
        buttonSix.setOnClickListener { makeOperation("6") }
        buttonSeven.setOnClickListener { makeOperation("7") }
        buttonEight.setOnClickListener { makeOperation("8") }
        buttonNine.setOnClickListener { makeOperation("9") }
        buttonZero.setOnClickListener { makeOperation("0") }
        buttonPlus.setOnClickListener { makeOperation("+") }
        buttonMinus.setOnClickListener { makeOperation("-") }
        buttonMult.setOnClickListener { makeOperation("*") }
        buttonDiv.setOnClickListener { makeOperation("/") }
        ac.setOnClickListener { clearSpace() }
        openBrace.setOnClickListener { makeOperation("(") }
        closeBrace.setOnClickListener { makeOperation(")") }
        backspace.setOnClickListener { backspace() }
        buttonEqual.setOnClickListener { equals() }


    }

    private fun makeOperation(str: String) {
        if (binding.workPlace.text[0] == '0') {
            binding.workPlace.text = ""
        }

        binding.workPlace.append(str)
    }

    private fun clearSpace() {
        binding.workPlace.text = "0"
        binding.resultPlace.text = ""
    }

    private fun equals() {

        val operatedExpression = binding.workPlace.text.toString()
        try {
            val operatedResult = Calculator().calculate(operatedExpression)
            binding.resultPlace.text = operatedResult
            operationHistory.add("$operatedExpression = $operatedResult")
        } catch (e: Exception) {
            Toast.makeText(context, "Incorrect input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backspace() {

        val operatedText = binding.workPlace.text.toString()
        when {
            operatedText.length > 1 -> {
                binding.workPlace.text = operatedText.dropLast(1)
            }
            operatedText.length == 1 -> {
                binding.workPlace.text = "0"
            }
            else -> Toast.makeText(context, "Nothing in space", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}