package com.jgomez.discover_domain.usecase

import android.util.Log
import com.jgomez.common_utils.Resource
import com.jgomez.discover_domain.model.User
import com.jgomez.discover_domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SetUserInformation (private val sharedPreferencesRepository: SharedPreferencesRepository) {
    operator fun invoke(user: User): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(sharedPreferencesRepository.setUser(user)))
        } catch (e: Exception) {
            if(e.message != null)
                emit(Resource.Error(message = e.message.toString()))
        }
    }
}