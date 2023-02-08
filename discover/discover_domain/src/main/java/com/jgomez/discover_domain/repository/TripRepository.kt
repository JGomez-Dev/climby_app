package com.jgomez.discover_domain.repository

import com.jgomez.discover_domain.model.Trip

interface TripRepository {
    suspend fun getTrips(): List<Trip>
}