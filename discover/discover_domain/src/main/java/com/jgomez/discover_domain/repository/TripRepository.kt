package com.jgomez.discover_domain.repository

import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_domain.model.TripDetail

interface TripRepository {
    suspend fun getCardsInformation(): List<CardInformation>
    suspend fun getTripDetail(idTrip: Int): TripDetail
}