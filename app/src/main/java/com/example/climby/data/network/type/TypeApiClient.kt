package com.example.climby.data.network.type

import com.example.climby.data.model.types.TypesModel
import retrofit2.Response
import retrofit2.http.GET

interface TypeApiClient {

    @GET("type/types")
    suspend fun getTypes() : Response<List<TypesModel>>
}