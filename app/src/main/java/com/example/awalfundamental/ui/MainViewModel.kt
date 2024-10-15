package com.example.awalfundamental.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awalfundamental.data.response.EventResponse
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.data.retrofit.ApiConfig
import com.example.awalfundamental.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    private lateinit var binding: ActivityMainBinding
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        findEvents()
    }

    private fun findEvents(active: Int = 0) {
        showLoading(false)
//        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response: Response<EventResponse> = ApiConfig.getApiService().getEvents(active)
                withContext(Dispatchers.Main) {
                    showLoading(false)
//                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _listEvents.value = response.body()?.listEvents ?: listOf()
                    } else {
                        _errorMessage.value = "Error: ${response.message()}"
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showLoading(false)
//                    _isLoading.value = false
                    _errorMessage.value = "Exception: ${e.message}"
                    Log.e(TAG, "onFailure: ${e.message}")
                }
            }
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