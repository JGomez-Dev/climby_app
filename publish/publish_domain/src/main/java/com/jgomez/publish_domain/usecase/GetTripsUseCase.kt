package com.jgomez.publish_domain.usecase

import com.jgomez.common_utils.Resource
import com.jgomez.publish_domain.model.Trip
import com.jgomez.publish_domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTripsUseCase(private val tripRepository: TripRepository) {
    operator fun invoke(): Flow<Resource<List<Trip>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = tripRepository.getTrips()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}