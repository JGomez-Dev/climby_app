package com.example.climby.domain.booking

import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.repository.BookingRepository
import javax.inject.Inject

class Delete @Inject constructor(private val repository : BookingRepository) {

    suspend operator fun invoke(idBooking: Int): BookingModel = repository.deleteBooking(idBooking)

}