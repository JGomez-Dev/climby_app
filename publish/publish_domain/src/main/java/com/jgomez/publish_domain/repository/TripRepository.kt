package com.jgomez.publish_domain.repository

import com.jgomez.publish_domain.model.Trip

interface TripRepository {
    suspend fun getTrips(): List<Trip>
}