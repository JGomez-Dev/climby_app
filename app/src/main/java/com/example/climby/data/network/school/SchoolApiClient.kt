package com.example.climby.data.network.school

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SchoolApiClient {

    @GET("school")
    suspend fun getSchools() : Response<List<String>>

    @POST("school")
    suspend fun postSchool(@Body string: String): Response<String>

}