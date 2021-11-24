package com.example.climby.domain.user

import com.example.climby.data.model.user.UserModel
import com.example.climby.data.repository.UserRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : UserRepository) {

    suspend operator fun invoke(userModel: UserModel): UserModel = repository.postUser(userModel)

}