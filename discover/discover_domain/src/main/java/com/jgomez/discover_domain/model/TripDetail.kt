package com.jgomez.discover_domain.model

data class TripDetail(
    val id: Int,
    val title: String,
    val owner: User,
    val reservations: List<Booking>,
)