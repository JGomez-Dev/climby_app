package com.jgomez.discover_data.mapper

import com.google.gson.annotations.SerializedName
import com.jgomez.common_utils.DateUtils
import com.jgomez.discover_data.R
import com.jgomez.discover_data.model.*
import com.jgomez.discover_domain.model.*

fun TripResponseItemDTO.toDomainCardInformation(): CardInformation {
    return CardInformation(
        id = id,
        availablePlaces = availablePlaces.toString() + if (availablePlaces > 1) " plazas" else " plaza",
        climbingType = "$climbingType en",
        owner = owner.toDomainUser(),
        schoolPhoto = R.drawable.albarracin,
        usersPhoto = reservations.map { it.toDomainBooking().user.userPhoto }.plus(owner.userPhoto),
        school = "$school, ",
        departureDate = DateUtils().dateToStringFormat(departureDate)
    )
}

fun UserDTO.toDomainUser(): User {
    return User(
        email = this.email,
        experience = this.experience,
        fullName = this.fullName,
        outputs = this.outputs,
        phone = this.phone,
        ratings = this.ratings,
        score = this.score,
        userPhoto = this.userPhoto
    )
}

fun ReservationDTO.toDomainBooking(): Booking {
    return Booking(
        date_reservation = this.date_reservation,
        id_reservation = this.id_reservation,
        id_travel = this.id_travel,
        message = this.message.toDomainMessage(),
        reservation_status = this.reservation_status,
        user = this.user.toDomainUser(),
        valuation_status = this.valuation_status
    )
}

fun MessageDTO.toDomainMessage(): Message {
    return Message(
        id_message = this.id_message,
        read = this.read,
        text_message = this.text_message
    )
}


fun TripResponseItemDTO.toDomainTripDetail(): TripDetail {
    return TripDetail(
        id = id,
        title = "$school," + departureDate.take(3),
        owner = owner.toDomainUser(),
        reservations = reservations.map { it.toDomainBooking() }
    )

}