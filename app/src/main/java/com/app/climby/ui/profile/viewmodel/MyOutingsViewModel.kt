package com.app.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.trip.GetTripsUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class MyOutingsViewModel @Inject constructor(private val getTripsUser: GetTripsUser, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    val isLoading = MutableLiveData<Boolean>()
    var result: List<TripModel> = emptyList()

    fun getMyTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            result = getTripsUser(sharedPref.getInt("id", 0))
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            else
                tripsModel.postValue(result)
            isLoading.postValue(false)
        }
    }
}