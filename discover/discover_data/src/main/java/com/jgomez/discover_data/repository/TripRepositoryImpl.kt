package com.jgomez.discover_data.repository

import com.jgomez.discover_data.datasource.trip.LocalDataSource
import com.jgomez.discover_data.mapper.toDomainTrip
import com.jgomez.discover_data.network.TripApiService
import com.jgomez.discover_domain.model.*
import com.jgomez.discover_domain.repository.TripRepository


class TripRepositoryImpl(private val tripApiService: TripApiService) : TripRepository {
    override suspend fun getTrips(): List<Trip> {
        //return tripApiService.getTrips().map { it.toDomainTrip() }
        return LocalDataSource().getTrips()
    }
}