package com.example.climby.data.repository

import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.data.network.booking.BookingService
import javax.inject.Inject

class BookingRepository @Inject constructor(private val api : BookingService){
    suspend fun putBooking(bookingModel: BookingModel) = api.putBooking(bookingModel)
    suspend fun postBooking(bookingModel: BookingModel) = api.postBooking(bookingModel)

}