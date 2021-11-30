package com.example.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.trip.GetAllTrips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class ComingOutingsViewModel @Inject constructor(private val getAllTrips: GetAllTrips, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    private val isLoading = MutableLiveData<Boolean>()
    lateinit var result: List<TripModel>

    fun getMyTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            result = getAllTrips()
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            isLoading.postValue(false)
        }
    }
}