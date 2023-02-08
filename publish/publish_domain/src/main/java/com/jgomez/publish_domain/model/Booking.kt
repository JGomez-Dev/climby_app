package com.jgomez.publish_domain.model

data class Booking(
    val id: Int,
    val tripId: Int,
    val passenger: User,
    val status: Boolean,
    val valuationStatus: Boolean,
    val date: String,
    val message: Message,
)