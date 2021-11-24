package com.example.climby.data.model.booking

import com.example.climby.data.model.province.ProvinceModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class BookingModel (
        @SerializedName("id") val id: Int,
        @SerializedName("userId") val passenger: UserModel,
        @SerializedName("idTravel") val tripId: Int,
        @SerializedName("reservationStatus") val status: Boolean?,
        @SerializedName("valuationStatus") val valuationStatus: Boolean?,
        @SerializedName("date") val date: String,
        )

