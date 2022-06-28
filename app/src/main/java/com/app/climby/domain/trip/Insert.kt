package com.app.climby.domain.trip

import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.repository.TripRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : TripRepository) {
    suspend operator fun invoke(tripModel: TripModel): TripModel = repository.postTrip(tripModel)
}