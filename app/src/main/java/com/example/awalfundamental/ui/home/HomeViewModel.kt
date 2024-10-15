package com.example.awalfundamental.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awalfundamental.data.response.EventResponse
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.data.retrofit.ApiConfig
import com.example.awalfundamental.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchUpcomingEvents()
    }

    private fun fetchUpcomingEvents() = viewModelScope.launch {
        _isLoading.value = true
            try {
                val response = ApiConfig.getApiService().getEvents(1)
                if(response.isSuccessful){
                    _upcomingEvents.value = response.body()?.listEvents ?: listOf()
                    _isLoading.value = false

                }else{
                    showLoading(false)
                    _upcomingEvents.value = listOf()
                }
            }catch (e: Exception){
                _isLoading.value = false
                _upcomingEvents.value = listOf()
            }
        }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}