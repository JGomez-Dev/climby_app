package com.app.climby.data.network.message

import com.app.climby.data.model.message.MessageModel
import com.app.climby.data.model.trip.TripModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessageService @Inject constructor(private val api: MessageApiClient) {

    suspend fun markMessagesAsReadByTripId(id: Int): List<MessageModel> {
        return withContext(Dispatchers.IO) {
            val response = api.markMessagesAsReadByTripId(id)
            response.body() ?: emptyList()
        }
    }

}