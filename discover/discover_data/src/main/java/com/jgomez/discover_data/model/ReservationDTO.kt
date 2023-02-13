package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName

data class ReservationDTO(
    @SerializedName("date_reservation") val date_reservation: String,
    @SerializedName("id_reservation") val id_reservation: Int,
    @SerializedName("id_travel") val id_travel: Int,
    @SerializedName("message") val message: MessageDTO,
    @SerializedName("reservation_status") val reservation_status: Boolean,
    @SerializedName("user") val user: UserDTO,
    @SerializedName("valuation_status") val valuation_status: Boolean
)