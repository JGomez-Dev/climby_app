package com.jgomez.discover_domain.repository

import com.jgomez.discover_domain.model.CardInformation

interface TripRepository {
    suspend fun getCardsInformation(): List<CardInformation>
}