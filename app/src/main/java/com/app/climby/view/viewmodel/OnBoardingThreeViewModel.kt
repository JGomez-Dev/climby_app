package com.app.climby.view.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.booking.PutBooking
import com.app.climby.domain.user.Update
import com.app.climby.util.Commons
import com.app.climby.view.activity.OnBoardingThreeActivity
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingThreeViewModel @Inject constructor(private val putUser: Update, private val putBooking: PutBooking) : ViewModel() {

    val isComplete = MutableLiveData<Boolean>()

    fun updateBooking(bookingModel: BookingModel, trip: TripModel, applicationContext: Context, requestsActivity: OnBoardingThreeActivity, notify: Boolean, withTrip: Boolean) {
        viewModelScope.launch {
            if(withTrip){
                //TODO Cuando eduardo cambie el update de viajes se podrá quitar en teoria
                putBooking(bookingModel)
                putUser(trip.driver!!)
                trip.bookings?.forEach {
                    if(it.passenger?.email != Commons.userSession?.email){
                        putUser(it.passenger!!)
                    }
                }
                isComplete.postValue(true)
            }else{
                putBooking(bookingModel)
                isComplete.postValue(true)
            }
            if (notify) {
                Commons.sendNotification(
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