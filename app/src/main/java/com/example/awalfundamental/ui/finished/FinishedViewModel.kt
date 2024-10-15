package com.example.awalfundamental.ui.finished

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awalfundamental.data.response.EventResponse
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class FinishedViewModel : ViewModel() {
    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchFinishedEvents()
    }

    private fun fetchFinishedEvents() = viewModelScope.launch {
        _isLoading.value = true

            try {
                val response: Response<EventResponse> = ApiConfig.getApiService().getEvents(active = 0)
                if(response.isSuccessful){
                    _isLoading.value = false
                    _finishedEvents.value = response.body()?.listEvents ?: listOf()
                }else{
                    _isLoading.value = false
                    _finishedEvents.value = listOf()
                }
            }catch (e: Exception){
                _isLoading.value = false
                _finishedEvents.value = listOf()
            }

    }
}