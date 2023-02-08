package com.jgomez.discover_domain.repository

import com.jgomez.discover_domain.model.Province


interface ProvinceRepository {
    suspend fun getProvinces(): List<Province>
}