package com.app.climby.domain.trip

import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.user.UserModel
import com.app.climby.data.repository.TripRepository
import com.app.climby.data.repository.UserRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : TripRepository) {

    suspend operator fun invoke(tripModel: TripModel): TripModel = repository.postTrip(tripModel)

}