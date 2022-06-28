package com.app.climby.domain.user

import com.app.climby.data.model.user.UserModel
import com.app.climby.data.repository.UserRepository
import javax.inject.Inject

class GetByEmail @Inject constructor(private val repository : UserRepository) {
    suspend operator fun invoke(email: String): UserModel = repository.getUserByEmail(email)
}