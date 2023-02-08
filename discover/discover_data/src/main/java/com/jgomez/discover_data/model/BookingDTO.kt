package com.jgomez.discover_data.model

data class BookingDTO(
    val id: Int,
    val tripId: Int,
    val passenger: UserDTO,
    val status: Boolean,
    val valuationStatus: Boolean,
    val date: String,
    val message: MessageDTO,
)
