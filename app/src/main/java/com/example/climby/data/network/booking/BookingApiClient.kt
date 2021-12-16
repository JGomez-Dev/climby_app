package com.example.climby.data.network.booking

import com.example.climby.data.model.booking.BookingModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface BookingApiClient {
    @PUT("reservation")
    suspend fun putBooking(@Body bookingModel: BookingModel?): Response<BookingModel>
}