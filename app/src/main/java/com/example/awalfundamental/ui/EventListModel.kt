package com.example.awalfundamental.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awalfundamental.data.response.EventResponse
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class EventListModel: ViewModel() {
    private val _listEvents = MutableLiveData<List<ListEventsItem>>()
    val listEvents: LiveData<List<ListEventsItem>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "EventListModel"
    }

    init {
        findEvents()
    }

    fun findEvents(active: Int = 0) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: Response<EventResponse> = ApiConfig.getApiService().getEvents(active)
                _isLoading.value =false
                if(response.isSuccessful){
                    _listEvents.value = response.body()?.listEvents ?: listOf()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }catch (e: Exception){
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${e.message}")
            }
        }
    }
}