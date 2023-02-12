package com.jgomez.discover_data.network

import com.jgomez.discover_data.model.TripResponseDTO
import retrofit2.http.GET

interface TripApiService {

    @GET("/travels")
    suspend fun getTrips() : TripResponseDTO

}