package com.app.climby.domain.trip

import com.app.climby.data.repository.TripRepository
import javax.inject.Inject

class DeleteTrip @Inject constructor(private val repository : TripRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteTrip(id)
}