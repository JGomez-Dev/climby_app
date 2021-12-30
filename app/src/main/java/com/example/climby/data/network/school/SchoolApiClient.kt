package com.example.climby.data.network.school

import com.example.climby.data.model.school.SchoolModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SchoolApiClient {

    @GET("school/schools")
    suspend fun getSchools() : Response<List<SchoolModel>>

    @POST("school")
    suspend fun postSchool(@Body string: String): Response<String>

}