package com.example.climby.domain.trip

import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.repository.TripRepository
import javax.inject.Inject

class GetTravelsWithUserBookings  @Inject constructor(private val repository : TripRepository) {
    suspend operator fun invoke(id: Int): List<TripModel> = repository.getTravelsWithUserReservation(id)
}