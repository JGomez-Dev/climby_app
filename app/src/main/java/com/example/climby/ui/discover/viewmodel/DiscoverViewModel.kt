package com.example.climby.ui.discover.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.trip.GetAllTrips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val getAllTrips: GetAllTrips) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    val isLoading = MutableLiveData<Boolean>()

    fun getTrips(id: Int) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAllTrips(id)
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            isLoading.postValue(false)
        }
    }
}