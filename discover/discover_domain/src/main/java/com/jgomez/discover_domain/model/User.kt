package com.jgomez.discover_domain.model

data class User(
    val email: String,
    val experience: String,
    val fullName: String,
    val outputs: Int,
    val phone: String,
    val ratings: Int,
    val score: Double,
    val userPhoto: String
)
