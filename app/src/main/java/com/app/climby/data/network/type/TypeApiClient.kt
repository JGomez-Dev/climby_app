package com.app.climby.data.network.type

import com.app.climby.data.model.types.TypesModel
import retrofit2.Response
import retrofit2.http.GET

interface TypeApiClient {

    @GET("type/types")
    suspend fun getTypes() : Response<List<TypesModel>>
}