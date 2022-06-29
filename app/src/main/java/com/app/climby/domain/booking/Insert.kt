package com.app.climby.domain.booking

import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.repository.BookingRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : BookingRepository) {
    suspend operator fun invoke(bookingModel: BookingModel): BookingModel = repository.postBooking(bookingModel)
}