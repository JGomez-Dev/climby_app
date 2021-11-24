package com.example.climby.data.model.trip

import com.example.climby.data.model.booking.BookingModel
import com.example.climby.data.model.province.ProvinceModel
import com.example.climby.data.model.school.SchoolModel
import com.example.climby.data.model.types.TypesModel
import com.example.climby.data.model.user.UserModel
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime

data class TripModel (
        @SerializedName("id") val id: Int,
        @SerializedName("school") val site: SchoolModel,
        @SerializedName("ClimbingType") val type: TypesModel,
        @SerializedName("numberSeats") val availablePlaces: Int,
        @SerializedName("Date") val departure: String,
        @SerializedName("idProvince") val province: ProvinceModel,
        @SerializedName("driver") val driver: UserModel,
        @SerializedName("reservation") val bookings: List<BookingModel>
        )

