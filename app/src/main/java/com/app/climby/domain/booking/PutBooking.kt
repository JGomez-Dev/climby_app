package com.app.climby.domain.booking

import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.repository.BookingRepository
import com.app.climby.data.repository.TripRepository
import javax.inject.Inject

class PutBooking  @Inject constructor(private val repository : BookingRepository) {
    suspend operator fun invoke(bookingModel: BookingModel): BookingModel = repository.putBooking(bookingModel)
}