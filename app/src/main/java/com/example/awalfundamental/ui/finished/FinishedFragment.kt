package com.example.awalfundamental.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awalfundamental.databinding.FragmentFinishedBinding
import com.example.awalfundamental.ui.EventAdapter
import com.example.awalfundamental.ui.finished.FinishedViewModel

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null

    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var finishedViewModel: FinishedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)

        finishedViewModel = ViewModelProvider(this).get(FinishedViewModel::class.java)

        binding.rvFinished.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = EventAdapter(listOf())
        binding.rvFinished.adapter = eventAdapter

        observeEvents()
        finishedViewModel.isLoading.observe(viewLifecycleOwner){ isLoading->
            binding?.progressBar?.visibility = if(isLoading) View.VISIBLE else View.GONE
        }

        return binding.root

    }

    private fun observeEvents() {
        finishedViewModel.finishedEvents.observe(viewLifecycleOwner, Observer{ events->
            if (events != null && events.isNotEmpty()){
                eventAdapter.updateData(events)
            }else{
                Toast.makeText(requireContext(), "No Events available", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}