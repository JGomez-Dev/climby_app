package com.jgomez.discover_domain.model

data class Trip (
    val id: Int,
    val school: School,
    val climbType: ClimbType,
    val availablePlaces: Int,
    val departureDate: String,
    val province: Province,
    val owner: User,
    val bookings: List<Booking>
)

