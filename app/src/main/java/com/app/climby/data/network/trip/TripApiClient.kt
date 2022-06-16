package com.app.climby.data.network.trip

import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.user.UserModel
import retrofit2.Response
import retrofit2.http.*

interface TripApiClient {

    @GET("travel/travels")
    suspend fun getTrips() : Response<List<TripModel>>

    @GET("travel/users/{id}")
    suspend fun getTripsUser(@Path("id") id: Int) : Response<List<TripModel>>

    @GET("travel/travel/{id}")
    suspend fun getTripById(@Path("id") id: Int) : Response<TripModel>

    @GET("travel/reservations/idUser/{id}")
    suspend fun getTravelsWithUserReservation(@Path("id") id: Int) : Response<List<TripModel>>

    @POST("travel")
    suspend fun postUser(@Body tripModel: TripModel?): Response<TripModel>

    @PUT("travel")
    suspend fun putTrip(@Body tripModel: TripModel?): Response<TripModel>

    @DELETE("travel/{id}")
    suspend fun deleteTrip(@Path("id") id: Int)
}