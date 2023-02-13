package com.jgomez.discover_domain.model

data class Booking(
    val date_reservation: String,
    val id_reservation: Int,
    val id_travel: Int,
    val message: Message,
    val reservation_status: Boolean,
    val user: User,
    val valuation_status: Boolean
)