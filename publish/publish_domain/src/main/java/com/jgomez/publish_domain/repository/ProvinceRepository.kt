package com.jgomez.publish_domain.repository

import com.jgomez.publish_domain.model.Province

interface ProvinceRepository {
    suspend fun getProvinces(): List<Province>
}