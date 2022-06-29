package com.app.climby.util

object UIConstants {

    const val ANIMATE: Long = 1000

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

enum class From(val status: String) {
    DISCOVER("discover"),
    COMING_OUTINGS("comingOutings"),
    PROFILE("profile"),
    PUBLISH("publish"),
    EDIT_TRIP("editTrip")
}