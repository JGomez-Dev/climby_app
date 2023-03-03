package com.jgomez.discover_domain.model

data class CardInformation(
    val id: Int,
    val availablePlaces: String,
    val climbingType: String,
    val owner: User,
    val schoolPhoto: Int,
    val usersPhoto: List<String>,
    val school: String,
    val departureDate: String
)

