package com.jgomez.discover_presentation.views

import com.jgomez.discover_domain.model.CardInformation

data class TripState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<CardInformation>? = null
)
