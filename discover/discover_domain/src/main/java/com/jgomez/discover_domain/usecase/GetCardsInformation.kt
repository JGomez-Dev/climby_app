package com.jgomez.discover_domain.usecase

import com.jgomez.common_utils.Resource
import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCardsInformation(private val tripRepository: TripRepository) {
    operator fun invoke(): Flow<Resource<List<CardInformation>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = tripRepository.getCardsInformation()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}