package com.jgomez.discover_domain.model

data class User(
    val name: String,
    val experience: String,
    val phone: String,
    val email: String,
    val score: Double,
    val numberRatings: Int,
    val numberOutings: Int,
    val urlPhoto: String,
    val token: String
)
