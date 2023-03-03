package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName

data class TripResponseItemDTO(
    @SerializedName("id_travel") val id: Int,
    @SerializedName("available_places") val availablePlaces: Int,
    @SerializedName("climbing_type") val climbingType: String,
    @SerializedName("departure_date") val departureDate: String,
    @SerializedName("organizer") val owner: UserDTO,
    @SerializedName("province") val province: String,
    @SerializedName("reservations") val reservations: List<ReservationDTO>,
    @SerializedName("school") val school: String
)