package com.app.climby.data.network.booking

import com.app.climby.data.model.booking.BookingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingService @Inject constructor(private val api: BookingApiClient) {

    suspend fun getNotificationByUserId(userId: Int): Int {
        return withContext(Dispatchers.IO) {
            val response = api.getNotificationByUserId(userId)
            response.body() ?: 0
        }
    }

    suspend fun putBooking(bookingModel: BookingModel): BookingModel {
        return withContext(Dispatchers.IO) {
            val response = api.putBooking(bookingModel)
            response.body()!!
        }
    }

    suspend fun postBooking(bookingModel: BookingModel): BookingModel {
        return withContext(Dispatchers.IO) {
            val response = api.postBooking(bookingModel)
            response.body()!!
        }
    }

    suspend fun deleteBooking(idBooking: Int) {
            api.deleteBooking(idBooking)
    }
}