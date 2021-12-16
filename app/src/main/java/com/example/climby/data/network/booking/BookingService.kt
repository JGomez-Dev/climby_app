package com.example.climby.data.network.booking

import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.network.trip.TripApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingService @Inject constructor(private val api: BookingApiClient) {

    suspend fun putBooking(bookingModel: BookingModel): BookingModel {
        return withContext(Dispatchers.IO) {
            val response = api.putBooking(bookingModel)
            response.body()!!
        }
    }
}