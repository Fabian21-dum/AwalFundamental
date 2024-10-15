package com.example.awalfundamental.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awalfundamental.databinding.FragmentHomeBinding
import com.example.awalfundamental.ui.DetailActivity
import com.example.awalfundamental.ui.EventAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = EventAdapter(listOf())
        showLoading(false)
        binding.rvHome.adapter = eventAdapter

        eventAdapter.setOnItemClickCallback(object: EventAdapter.OnItemClickCallback {
            override fun onItemClicked(eventId: String) {
                showLoading(false)
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                startActivity(intent)
            }
        })

        observeEvents()
        homeViewModel.isLoading.observe(viewLifecycleOwner){ isLoading->
            binding?.progressBar?.visibility = if(isLoading) View.VISIBLE else View.GONE
        }

        return binding.root

    }

    private fun observeEvents() {
        homeViewModel.upcomingEvents.observe(viewLifecycleOwner, Observer{ events->
            if (events != null && events.isNotEmpty()){
                showLoading(false)
                eventAdapter.updateData(events)
            }else{
                showLoading(false)
                Toast.makeText(requireContext(), "No Events available", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}