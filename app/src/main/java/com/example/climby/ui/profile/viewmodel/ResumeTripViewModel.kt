package com.example.climby.ui.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.trip.GetTripById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeTripViewModel @Inject constructor(private val _getTripById: GetTripById): ViewModel() {

    var tripModel = MutableLiveData<TripModel>()
    lateinit var result: TripModel

    fun getTripById(idTrip: Int) {
        viewModelScope.launch {
            result = _getTripById(idTrip)
            tripModel.postValue(result)
        }
    }
}