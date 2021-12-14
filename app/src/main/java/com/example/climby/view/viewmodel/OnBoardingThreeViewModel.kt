package com.example.climby.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnBoardingThreeViewModel @Inject constructor() : ViewModel() {

    fun putBooking(tripModel: TripModel) {
        viewModelScope.launch {
            /*val result = update(bookingModel)*/
        }
    }

}