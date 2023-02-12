package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName

data class TripResponseItemDTO(
    @SerializedName("available_places") val available_places: Int,
    @SerializedName("climbing_type") val climbing_type: String,
    @SerializedName("departure_date") val departure_date: String,
    @SerializedName("organizer") val owner: UserDTO,
    @SerializedName("province") val province: String,
    @SerializedName("reservations") val reservations: List<ReservationDTO>,
    @SerializedName("school") val school: String
)