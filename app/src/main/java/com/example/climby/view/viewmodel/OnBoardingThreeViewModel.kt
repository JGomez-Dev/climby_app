package com.example.climby.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.booking.PutBooking
import com.example.climby.domain.trip.PutTrip
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingThreeViewModel @Inject constructor(private val putTrip: PutTrip,private val putBooking: PutBooking) : ViewModel() {

    fun updateTrip(tripModel: TripModel) {
        viewModelScope.launch {
            /*val result = */putTrip(tripModel)
        }
    }

    fun updateBooking(bookingModel: BookingModel) {
        viewModelScope.launch {
            /*val result = */putBooking(bookingModel)
        }
    }
}