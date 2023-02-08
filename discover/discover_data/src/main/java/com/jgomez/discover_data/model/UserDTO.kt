package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("full_name") val name: String,
    @SerializedName("experience") val experience: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("score") val score: Double,
    @SerializedName("outputs") val numberRatings: Int,
    @SerializedName("outputs") val numberOutings: Int,
    @SerializedName("user_photo") val urlPhoto: String,
    @SerializedName("ratings") val token: String
)
