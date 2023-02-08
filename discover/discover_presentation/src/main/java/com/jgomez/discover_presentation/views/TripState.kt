package com.jgomez.discover_presentation.views

import com.jgomez.discover_domain.model.Trip

data class TripState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<Trip>? = null
)
