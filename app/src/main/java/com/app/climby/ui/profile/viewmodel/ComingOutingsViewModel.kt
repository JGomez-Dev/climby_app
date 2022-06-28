package com.app.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.booking.Delete
import com.app.climby.domain.trip.GetAllTrips
import com.app.climby.domain.trip.GetTravelsWithUserBookings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class ComingOutingsViewModel @Inject constructor(private val getTravelsWithUserBookings: GetTravelsWithUserBookings, private val delete: Delete, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    val isLoading = MutableLiveData<Boolean>()
    var result: List<TripModel> = emptyList()

    fun deleteBooking(idBooking: BookingModel) {
        viewModelScope.launch {
            delete(idBooking.id)
        }
    }

    fun getMyNextOutings() {
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