package com.app.climby.ui.profile.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.booking.PutBooking
import com.app.climby.domain.trip.GetTripById
import com.app.climby.ui.profile.RequestsActivity
import com.app.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.Console
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(private val putBooking: PutBooking, private val _getTripById: GetTripById): ViewModel() {

    var tripModel = MutableLiveData<TripModel>()
    lateinit var result: TripModel
    var isLoading = MutableLiveData<Boolean>()

    fun updateBooking(bookingModel: BookingModel, request: String, trip: TripModel, applicationContext: Context, requestsActivity: RequestsActivity) {
        viewModelScope.launch {
                isLoading.postValue(true)
                try{
                    val result = putBooking(bookingModel)
                    if (result != null) {
                        if(request == "accepted"){
                            Commons.sendNotification(
                                bookingModel.passenger?.token!!,
                                trip.driver?.name!!.split(" ")[0] + " te ha aceptado en su grupo",
                                "AuthActivity",
                                trip.id.toString(),
                                "TripUsersActivity",
                                trip.driver.name.split(" ")[0]  + " ha aceptado tu solicitud para la salida a " + trip.site?.name + " el " + trip.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip.departure.toString() + ". Pronto te contactará. "),
                                applicationContext,
                                requestsActivity
                            )
                        }else if(request == "refuse"){
                            Commons.sendNotification(
                                bookingModel.passenger?.token!!,
                                trip.driver?.name!!.split(" ")[0] + " ha rechazado tu solicitud",
                                "AuthActivity",
                                "",
                                "ProfileFragment",
                                trip.driver.name.split(" ")[0]  + " ha rechazado tu solicitud para la salida a " + trip.site?.name + " el " + trip.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip.departure.toString() + ". Pronto te contactará. "),
                                applicationContext,
                                requestsActivity
                            )
                        }
                    } else {
                        isLoading.postValue(false)
                    }
                }catch (e : Exception){
                    Log.e("Conexión", e.message.toString())
                }

        }
    }

    fun getTripById(idTrip: Int) {
        viewModelScope.launch {
            result = _getTripById(idTrip)
            tripModel.postValue(result)
        }
    }
}