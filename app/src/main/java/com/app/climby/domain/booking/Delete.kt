package com.app.climby.domain.booking

import com.app.climby.data.repository.BookingRepository
import javax.inject.Inject

class Delete @Inject constructor(private val repository : BookingRepository) {
    suspend operator fun invoke(idBooking: Int) = repository.deleteBooking(idBooking)
}