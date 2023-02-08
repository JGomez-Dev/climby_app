package com.jgomez.publish_data.repository

import com.jgomez.publish_data.mapper.toDomainProvince
import com.jgomez.publish_data.network.ProvinceApiService
import com.jgomez.publish_domain.model.Province
import com.jgomez.publish_domain.repository.ProvinceRepository

class ProvinceRepositoryImpl(private val provinceApiService: ProvinceApiService) : ProvinceRepository {
    override suspend fun getProvinces(): List<Province> {
        return provinceApiService.getProvinces().map { it.toDomainProvince() }
    }
}