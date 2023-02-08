package com.app.climby.data.repository

import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.network.booking.BookingService
import javax.inject.Inject

class BookingRepository @Inject constructor(private val api : BookingService){
    suspend fun getNotificationByUserId(userEmail: String) = api.getNotificationByUserEmail(userEmail)
    suspend fun putBooking(bookingModel: BookingModel) = api.putBooking(bookingModel)
    suspend fun postBooking(bookingModel: BookingModel) = api.postBooking(bookingModel)
    suspend fun deleteBooking(idBooking: Int) = api.deleteBooking(idBooking)
}