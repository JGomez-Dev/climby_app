package com.jgomez.discover_data.mapper

import com.jgomez.discover_data.model.*
import com.jgomez.discover_domain.model.*

fun TripResponseItemDTO.toDomainCardInformation(): CardInformation {
    return CardInformation(
        available_places = available_places.toString() + if(available_places > 1) " plazas" else " plaza",
        climbing_type = "$climbing_type en",
        owner = owner.toDomainUser(),
        users_photo = reservations.map { it.toDomainBooking().user.user_photo },
        school_and_departure_date = "$school ,$departure_date",
    )
}

fun UserDTO.toDomainUser(): User {
    return User(
        email = this.email,
        experience = this.experience,
        full_name = this.full_name,
        outputs = this.outputs,
        phone = this.phone,
        ratings = this.ratings,
        score = this.score,
        user_photo = this.user_photo
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