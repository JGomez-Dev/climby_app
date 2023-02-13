package com.jgomez.discover_data.datasource.trip

import com.jgomez.discover_domain.model.CardInformation

interface TripDataSource {
    suspend fun getTrips(): List<CardInformation>
}