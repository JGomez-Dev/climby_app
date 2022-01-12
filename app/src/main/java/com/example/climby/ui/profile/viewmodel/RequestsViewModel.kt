package com.example.climby.ui.profile.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.booking.PutBooking
import com.example.climby.ui.profile.RequestsActivity
import com.example.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(private val putBooking: PutBooking): ViewModel() {

    fun updateBooking(bookingModel: BookingModel, request: String, trip: TripModel, applicationContext: Context, requestsActivity: RequestsActivity) {
        viewModelScope.launch {
            putBooking(bookingModel)
            if(request == "accepted"){
                Commons.sendNotification(
                    bookingModel.passenger?.token!!,
                    trip.driver?.name!!.split(" ")[0] + " te ha aceptado en su grupo",
                    "OPEN_MainActivity",
                    "myOutigsFragment",
                    trip.driver.name.split(" ")[0]  + " ha aceptado tu solicitud para la salida a " + trip.site?.name + " el " + trip.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip.departure.toString() + ". Pronto te contactará. "),
                    applicationContext,
                    requestsActivity
                )
            }else if(request == "refuse"){
                Commons.sendNotification(
                    bookingModel.passenger?.token!!,
                    trip.driver?.name!!.split(" ")[0] + " ha rechazado tu solicitud",
                    "OPEN_MainActivity",
                    "myOutigsFragment",
                    trip.driver.name.split(" ")[0]  + " ha rechazado tu solicitud para la salida a " + trip.site?.name + " el " + trip.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip.departure.toString() + ". Pronto te contactará. "),
                    applicationContext,
                    requestsActivity
                )
            }
        }
    }

}