package com.jgomez.discover_data.datasource.trip

import com.jgomez.discover_data.mapper.toDomainCardInformation
import com.jgomez.discover_data.network.TripApiService
import com.jgomez.discover_domain.model.CardInformation

class RemoteDataSource (private val tripApiService: TripApiService) : TripDataSource{
    override suspend fun getTrips(): List<CardInformation> {
        return tripApiService.getTrips().map { it.toDomainCardInformation() }
    }
}