package com.jgomez.publish_domain.model

import java.util.ArrayList

data class Trip (
    val id: Int,
    val school: School,
    val climbType: ClimbType,
    val availablePlaces: Int,
    val departureDate: String,
    val province: Province,
    val owner: User,
    val bookings: ArrayList<Booking>
)

