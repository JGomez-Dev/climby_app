package com.app.climby.data.model.user

import com.app.climby.data.model.trip.TripModel

class UserProvider {
    companion object {
        var users: List<UserModel> = emptyList()
    }
}