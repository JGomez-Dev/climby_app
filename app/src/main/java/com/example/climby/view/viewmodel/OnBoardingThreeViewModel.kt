package com.example.climby.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.booking.PutBooking
import com.example.climby.domain.trip.PutTrip
import com.example.climby.utils.Commons
import com.example.climby.view.activity.OnBoardingThreeActivity
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingThreeViewModel @Inject constructor(private val putTrip: PutTrip, private val putBooking: PutBooking) : ViewModel() {

    fun updateTrip(tripModel: TripModel) {
        viewModelScope.launch {
            /*val result = */putTrip(tripModel)
        }
    }

    fun updateBooking(bookingModel: BookingModel, trip: TripModel?, applicationContext: Context, requestsActivity: OnBoardingThreeActivity) {
        viewModelScope.launch {
            /*val result = */putBooking(bookingModel)
            Commons.sendNotification(
                bookingModel.passenger?.token!!,
                bookingModel.passenger.name!!.split(" ")[0] + " te ha enviado un mensaje",
                "OPEN_ResumeTripActivity",
                "",
                bookingModel.passenger.name.split(" ")[0]  + " te ha enviado un mensaje acerca de la salida a " + trip?.site?.name + " el " + trip?.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip?.departure.toString() + "."),
                applicationContext,
                requestsActivity
            )
        }
    }
}