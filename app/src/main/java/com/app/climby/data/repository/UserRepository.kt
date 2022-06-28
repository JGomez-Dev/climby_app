package com.app.climby.data.repository

import com.app.climby.data.model.user.UserModel
import com.app.climby.data.network.user.UserService
import javax.inject.Inject

class UserRepository @Inject constructor(private val api : UserService){
    suspend fun postUser(userModel: UserModel) = api.postUser(userModel)
    suspend fun putUser(userModel: UserModel) = api.putUser(userModel)
    suspend fun getUser(id: Int) = api.getUser(id)
    suspend fun getUserByEmail(email: String) = api.getUserByEmail(email)
}