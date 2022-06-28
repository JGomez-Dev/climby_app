package com.app.climby.data.repository

import com.app.climby.data.model.message.MessageModel
import com.app.climby.data.network.message.MessageService
import javax.inject.Inject

class MessageRepository @Inject constructor(private val api : MessageService){
    suspend fun markMessagesAsReadByTripId(id: Int): List<MessageModel> = api.markMessagesAsReadByTripId(id)
}