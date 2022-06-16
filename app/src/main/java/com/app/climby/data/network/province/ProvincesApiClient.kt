package com.app.climby.data.network.province

import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.data.model.trip.TripModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProvincesApiClient {

    @GET("province/provinces")
    suspend fun getProvinces() : Response<List<ProvinceModel>>

}