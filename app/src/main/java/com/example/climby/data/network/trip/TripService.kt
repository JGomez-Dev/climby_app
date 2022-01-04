package com.example.climby.data.network.trip

import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TripService @Inject constructor(private val api: TripApiClient) {


    suspend fun getTrips(): List<TripModel>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTrips()
                response.body() ?: emptyList()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getTripsUser(id: Int): List<TripModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getTripsUser(id)
            response.body() ?: emptyList()
        }
    }

    suspend fun getTravelsWithUserReservation(id: Int): List<TripModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getTravelsWithUserReservation(id)
            response.body() ?: emptyList()
        }
    }

    suspend fun postTrip(tripModel: TripModel): TripModel {
        return withContext(Dispatchers.IO) {
            val response = api.postUser(tripModel)
            response.body()!!
        }
    }

    suspend fun putTrip(tripModel: TripModel): TripModel {
        return withContext(Dispatchers.IO) {
            val response = api.putTrip(tripModel)
            response.body()!!
        }
    }

    suspend fun deleteTrip(id: Int) {
        api.deleteTrip(id)
    }
}