package com.jgomez.discover_domain.usecase

import com.jgomez.common_utils.Resource
import com.jgomez.discover_domain.model.User
import com.jgomez.discover_domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserInformation(private val sharedPreferencesRepository: SharedPreferencesRepository) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = sharedPreferencesRepository.getUser()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}