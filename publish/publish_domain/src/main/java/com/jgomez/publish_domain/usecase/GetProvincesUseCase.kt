package com.jgomez.publish_domain.usecase

import com.jgomez.common_utils.Resource
import com.jgomez.publish_domain.model.Province
import com.jgomez.publish_domain.repository.ProvinceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProvincesUseCase(private val provinceRepository: ProvinceRepository) {
    operator fun invoke(): Flow<Resource<List<Province>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(data = provinceRepository.getProvinces()))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }
}