package com.jgomez.discover_domain.model

data class CardInformation(
    val available_places: String,
    val climbing_type: String,
    val owner: User,
    val users_photo: List<String>,
    val school_and_departure_date: String
)

