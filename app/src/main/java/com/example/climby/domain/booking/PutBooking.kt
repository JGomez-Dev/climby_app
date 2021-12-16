package com.example.climby.domain.booking

import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.repository.BookingRepository
import com.example.climby.data.repository.TripRepository
import javax.inject.Inject

class PutBooking  @Inject constructor(private val repository : BookingRepository) {
    suspend operator fun invoke(bookingModel: BookingModel): BookingModel = repository.putBooking(bookingModel)
}