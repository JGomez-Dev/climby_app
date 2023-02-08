package com.jgomez.discover_data.datasource.trip

import com.jgomez.discover_data.mapper.toDomainTrip
import com.jgomez.discover_data.network.TripApiService
import com.jgomez.discover_domain.model.Trip

class RemoteDataSource (private val tripApiService: TripApiService) : TripDataSource{
    override suspend fun getTrips(): List<Trip> {
        return tripApiService.getTrips().map { it.toDomainTrip() }
    }
}