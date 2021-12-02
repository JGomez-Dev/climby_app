package com.example.climby.data.network.trip

import com.example.climby.data.model.trip.TripModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TripApiClient {

    @GET("travel/travels")
    suspend fun getTrips() : Response<List<TripModel>>

    @GET("travel/users/{id}")
    suspend fun getTripsUser(@Path("id") id: Int) : Response<List<TripModel>>

    @GET("travel/reservations/idUser/{id}")
    suspend fun getTravelsWithUserReservation(@Path("id") id: Int) : Response<List<TripModel>>

}