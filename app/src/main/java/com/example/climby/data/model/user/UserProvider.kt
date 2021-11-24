package com.example.climby.data.model.user

import com.example.climby.data.model.trip.TripModel

class UserProvider {
    companion object {
        var users: List<UserModel> = emptyList()
    }
}