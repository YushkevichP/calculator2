package com.example.homework1calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework1calculator.adapter.HistoryAdapter
import com.example.homework1calculator.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "Oops"
        }

    private val historyAdapter by lazy { HistoryAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentHistoryBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyList = arguments?.getStringArrayList(KEY_HISTORY)
            ?: emptyList<String>() // если аргументы пустые, то вернем путсой лист, а если все ок то получим по ключу наш лист


        with(binding) {
            recyclerView.adapter = historyAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        historyAdapter.submitList(historyList)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_HISTORY = "key_history"

        fun newInstance(list: List<String>): Fragment {
            return HistoryFragment()
                .apply {
                    arguments = bundleOf(
                        KEY_HISTORY to list
                    )
                }
        }
    }
}