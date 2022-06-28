package com.app.climby.data.network.message

import com.app.climby.data.model.message.MessageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageApiClient {

    @GET("message/travel/{id}")
    suspend fun markMessagesAsReadByTripId(@Path("id") id: Int): Response<List<MessageModel>>
}