package com.app.climby.data.network.province

import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.data.model.trip.TripModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProvinceService @Inject constructor(private val api: ProvincesApiClient) {
    suspend fun getProvinces(): List<ProvinceModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getProvinces()
            response.body() ?: emptyList()
        }
    }
}