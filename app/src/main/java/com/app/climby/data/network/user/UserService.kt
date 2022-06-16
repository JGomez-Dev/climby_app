package com.app.climby.data.network.user

import com.app.climby.data.model.user.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(private val userApiClient: UserApiClient) {

    suspend fun postUser(userModel: UserModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userApiClient.postUser(userModel)
            response.body()!!
        }
    }

    suspend fun putUser(userModel: UserModel): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userApiClient.putUser(userModel)
            response.body()!!
        }
    }

    suspend fun getUser(id: Int): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userApiClient.getUser(id)
            response.body()!!
        }
    }

    suspend fun getUserByEmail(email: String): UserModel {
        return withContext(Dispatchers.IO) {
            val response = userApiClient.getUserBymail(email)
            response.body()!!
        }
    }
}