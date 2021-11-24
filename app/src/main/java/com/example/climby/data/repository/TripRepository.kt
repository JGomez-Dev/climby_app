package com.example.climby.data.repository

import com.example.climby.data.network.trip.TripService
import javax.inject.Inject

class TripRepository @Inject constructor(private val api : TripService){
    suspend fun getTrips(id: Int) = api.getTrips(id)
}