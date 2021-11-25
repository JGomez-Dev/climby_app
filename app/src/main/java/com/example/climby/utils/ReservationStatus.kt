package com.example.climby.utils

enum class ReservationStatus(val status: Boolean?) {
    UNANSWERED(null),
    ACCEPTED(true),
    REFUSE(false),
}