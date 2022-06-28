package com.app.climby.data.network.user

import com.app.climby.data.model.user.UserModel
import retrofit2.Response
import retrofit2.http.*

interface UserApiClient {

    @POST("user")
    suspend fun postUser(@Body userModel: UserModel?): Response<UserModel>

    @PUT("user")
    suspend fun putUser(@Body userModel: UserModel?): Response<UserModel>

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<UserModel>

    @GET("user/email/{email}")
    suspend fun getUserBymail(@Path("email") email: String): Response<UserModel>

}
