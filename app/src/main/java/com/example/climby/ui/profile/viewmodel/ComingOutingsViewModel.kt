package com.example.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.trip.GetAllTrips
import com.example.climby.domain.trip.GetTravelsWithUserBookings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class ComingOutingsViewModel @Inject constructor(private val getTravelsWithUserBookings: GetTravelsWithUserBookings, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    val isLoading = MutableLiveData<Boolean>()
    var result: List<TripModel> = emptyList()

    fun getMyTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            result = getTravelsWithUserBookings(sharedPref.getInt("id", 0))
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            else
                tripsModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}