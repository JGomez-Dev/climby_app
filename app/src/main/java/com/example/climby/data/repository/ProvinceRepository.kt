package com.example.climby.data.repository

import com.example.climby.data.network.province.ProvinceService
import com.example.climby.data.network.trip.TripService
import javax.inject.Inject

class ProvinceRepository @Inject constructor(private val api : ProvinceService){
    suspend fun getProvinces() = api.getProvinces()
}