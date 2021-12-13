package com.example.climby.domain.trip

import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.data.repository.TripRepository
import com.example.climby.data.repository.UserRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : TripRepository) {

    suspend operator fun invoke(tripModel: TripModel): TripModel = repository.postTrip(tripModel)

}