package com.app.climby.util

object UIConstants {
}

enum class UserExperience(val status: String){
    BEGINNER("Principiante"),
    MEDIUM ("Intermedio"),
    ADVANCED("Experimentado"),
}

enum class ReservationStatus(val status: Boolean?) {
    REFUSE(null),
    ACCEPTED(true),
    UNANSWERED(false),
}