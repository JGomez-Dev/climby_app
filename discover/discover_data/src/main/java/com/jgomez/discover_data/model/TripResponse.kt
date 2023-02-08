package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class TripResponse(
    @SerializedName("") val id: Int,
    @SerializedName("school") val school: SchoolDTO,
    @SerializedName("climbing_type") val climbType: ClimbTypeDTO,
    @SerializedName("available_places") val availablePlaces: Int,
    @SerializedName("departure_date") val departureDate: String,
    @SerializedName("province") val province: ProvinceDTO,
    @SerializedName("organizer") val owner: UserDTO,
    @SerializedName("") val bookings: ArrayList<BookingDTO>
)