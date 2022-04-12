package com.example.homework1calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework1calculator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment(){

    private var _binding: FragmentCalculatorBinding ?=null
    private val binding get() =  requireNotNull(_binding){"Binding is empty"}

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

        //todo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}