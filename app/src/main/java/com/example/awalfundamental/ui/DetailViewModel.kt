package com.example.awalfundamental.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awalfundamental.data.response.DetailEventResponse
import com.example.awalfundamental.data.response.EventResponse
import com.example.awalfundamental.data.response.ListEventsItem
import com.example.awalfundamental.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val apiService = ApiConfig.getApiService()
    private val _eventDetail =MutableLiveData<DetailEventResponse>()
    val eventDetail: LiveData<DetailEventResponse> get() =_eventDetail
    private val _event = MutableLiveData<List<ListEventsItem>>()
    val event: LiveData<List<ListEventsItem>> = _event

    fun fetchDetailEvent(eventId: String){
        viewModelScope.launch {
            try {
                val response: Response<DetailEventResponse> = apiService.getDetail(eventId)
                if (response.isSuccessful){
                    _eventDetail.value = response.body()
                }else{
                    Log.e("DetailViewModel", "Error fetching event detail: ${response.message()}")
                }
            }catch (e: Exception){
                Log.e("DetailViewModel", "Exception: ${e.message}")
            }
        }
    }
}