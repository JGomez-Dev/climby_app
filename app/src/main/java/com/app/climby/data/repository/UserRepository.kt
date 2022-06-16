package com.app.climby.data.repository

import com.app.climby.data.model.user.UserModel
import com.app.climby.data.network.user.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService : UserService){

    suspend fun postUser(userModel: UserModel) = userService.postUser(userModel)

    suspend fun putUser(userModel: UserModel) = userService.putUser(userModel)

    suspend fun getUser(id: Int) = userService.getUser(id)

    suspend fun getUserByEmail(email: String) = userService.getUserByEmail(email)
}