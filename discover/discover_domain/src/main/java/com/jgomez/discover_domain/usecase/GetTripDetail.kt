package com.jgomez.discover_domain.usecase

import com.jgomez.common_utils.Resource
import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_domain.model.TripDetail
import com.jgomez.discover_domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTripDetail (private val tripRepository: TripRepository) {
    operator fun invoke(idTrip: Int): Flow<Resource<TripDetail>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = tripRepository.getTripDetail(idTrip)))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}