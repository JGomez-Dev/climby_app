package com.jgomez.discover_data.repository

import com.jgomez.discover_data.mapper.toDomainCardInformation
import com.jgomez.discover_data.mapper.toDomainTripDetail
import com.jgomez.discover_data.network.TripApiService
import com.jgomez.discover_domain.model.*
import com.jgomez.discover_domain.repository.TripRepository


class TripRepositoryImpl(private val tripApiService: TripApiService) : TripRepository {
    override suspend fun getCardsInformation(): List<CardInformation> {
        return tripApiService.getTrips().map { it.toDomainCardInformation() }
    }

    override suspend fun getTripDetail(idTrip: Int): TripDetail {
        return tripApiService.getTripDetail(idTrip).toDomainTripDetail()
    }
}