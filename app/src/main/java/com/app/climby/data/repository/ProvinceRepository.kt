package com.app.climby.data.repository

import com.app.climby.data.network.province.ProvinceService
import javax.inject.Inject

class ProvinceRepository @Inject constructor(private val api : ProvinceService){
    suspend fun getProvinces() = api.getProvinces()
}