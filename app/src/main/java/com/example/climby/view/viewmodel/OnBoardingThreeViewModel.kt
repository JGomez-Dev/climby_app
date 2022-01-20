package com.example.climby.view.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
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

    val isComplete = MutableLiveData<Boolean>()

    private fun updateTrip(tripModel: TripModel) {
        viewModelScope.launch {
            val result = putTrip(tripModel)
            isComplete.postValue(true)
        }
    }

    fun updateBooking(bookingModel: BookingModel, trip: TripModel, applicationContext: Context, requestsActivity: OnBoardingThreeActivity, notify: Boolean, withTrip: Boolean) {
        viewModelScope.launch {
            if(withTrip){
                //TODO Cuando eduardo cambie el update de viajes se podrá quitar en teoria
                putBooking(bookingModel)
                updateTrip(trip)
            }else{
                putBooking(bookingModel)
                isComplete.postValue(true)
            }
            if (notify) {
                Commons.sendNotificationTest(
                    trip.driver?.token!!,
                    bookingModel.passenger?.name!!.split(" ")[0] + " te ha enviado un mensaje",
                    "AuthActivity",
                    trip.id.toString(),
                    "ResumeTripActivity",
                    bookingModel.passenger.name.split(" ")[0] + " te ha enviado un mensaje acerca de la salida a " + trip?.site?.name + " el " + trip?.departure.toString().split(" ")[0].split("-")[2] + " de " + Commons.getDate(trip?.departure.toString() + "."),
                    applicationContext,
                    requestsActivity
                )
            }
        }
    }
}