package com.app.climby.domain.user

import com.app.climby.data.model.user.UserModel
import com.app.climby.data.repository.UserRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : UserRepository) {

    suspend operator fun invoke(userModel: UserModel): UserModel = repository.postUser(userModel)

}