package com.jgomez.discover_data.network

import com.jgomez.discover_data.model.TripResponseDTO
import com.jgomez.discover_data.model.TripResponseItemDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface TripApiService {

    @GET("/travels")
    suspend fun getTrips(): TripResponseDTO

    @GET("/travels/{id}")
    suspend fun getTripDetail(@Path("id") idTrip: Int): TripResponseItemDTO

}