package com.jgomez.discover_data.mapper

import com.jgomez.discover_data.model.*
import com.jgomez.discover_domain.model.*
import java.util.ArrayList

fun TripResponse.toDomainTrip(): Trip {
    return Trip(
        id = this.id,
        school = this.school.toDomainSchool(),
        climbType = this.climbType.toDomainClimbType(),
        availablePlaces = this.availablePlaces,
        departureDate = this.departureDate,
        province = this.province.toDomainProvince(),
        owner = this.owner.toDomainUser(),
        bookings = this.bookings.map { it.toDomainBooking() } as ArrayList<Booking>
    )
}

fun SchoolDTO.toDomainSchool(): School {
    return School(
        name = this.name
    )
}

fun ClimbTypeDTO.toDomainClimbType(): ClimbType {
    return ClimbType(
        name = this.name
    )
}

fun ProvinceDTO.toDomainProvince(): Province {
    return Province(
        name = this.name,
        numberTravels = this.numberTravels
    )
}

fun UserDTO.toDomainUser(): User {
    return User(
        name = this.name,
        experience = this.experience,
        phone = this.phone,
        email = this.email,
        score = this.score,
        numberRatings = this.numberRatings,
        numberOutings = this.numberOutings,
        urlPhoto = this.urlPhoto,
        token = this.token,
    )
}

fun BookingDTO.toDomainBooking(): Booking {
    return Booking(
        id = this.id,
        tripId = this.tripId,
        passenger = this.passenger.toDomainUser(),
        status = this.status,
        valuationStatus = this.valuationStatus,
        date = this.date,
        message = this.message.toDomainMessage(),
    )
}

fun MessageDTO.toDomainMessage(): Message {
    return Message(
        read = this.read,
        text = this.text,
    )
}