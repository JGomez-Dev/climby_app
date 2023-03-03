package com.jgomez.discover_data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
     @SerializedName("email") val email: String,
     @SerializedName("experience") val experience: String,
     @SerializedName("full_name") val fullName: String,
     @SerializedName("outputs") val outputs: Int,
     @SerializedName("phone") val phone: String,
     @SerializedName("ratings") val ratings: Int,
     @SerializedName("score") val score: Double,
     @SerializedName("user_photo") val userPhoto: String
)
