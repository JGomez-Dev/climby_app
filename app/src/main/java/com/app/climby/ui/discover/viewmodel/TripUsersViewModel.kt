package com.app.climby.ui.discover.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.booking.PutBooking
import com.app.climby.domain.trip.GetTripById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripUsersViewModel @Inject constructor(private val _getTripById: GetTripById): ViewModel() {

    var tripModel = MutableLiveData<TripModel>()
    lateinit var result: TripModel

    fun getTripById(idTrip: Int) {
        viewModelScope.launch {
            result = _getTripById(idTrip)
            tripModel.postValue(result)
        }
    }
}