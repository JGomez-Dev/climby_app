package com.jgomez.publish_data.network

import com.jgomez.publish_data.model.ProvinceResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProvinceApiService {

    @GET("province/provinces")
    suspend fun getProvinces() : List<ProvinceResponse>

}