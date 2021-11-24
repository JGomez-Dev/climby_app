package com.example.climby.data.model.user

import com.google.gson.annotations.SerializedName

data class UserModel(
        @SerializedName("id") val id: Int,
        @SerializedName("fullName") val name: String,
        @SerializedName("experience") val experience: String,
        @SerializedName("phone") val phone: String,
        @SerializedName("email") val email: String,
        @SerializedName("score") val score: Double,
        @SerializedName("outputs") val outings: Int,
        @SerializedName("userPhoto") val photo: String
        )
