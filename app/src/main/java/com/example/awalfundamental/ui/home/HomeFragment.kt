package com.example.awalfundamental.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awalfundamental.databinding.FragmentHomeBinding
import com.example.awalfundamental.ui.EventAdapter
import com.example.awalfundamental.ui.upcoming.UpcomingViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.rvAll.layoutManager =LinearLayoutManager(requireContext())
        eventAdapter = EventAdapter(listOf())
        binding.rvAll.adapter = eventAdapter

        observeEvents()

        return binding.root

    }

    private fun observeEvents() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}