package com.app.climby.domain.message

import com.app.climby.data.model.message.MessageModel
import com.app.climby.data.repository.MessageRepository
import javax.inject.Inject

class MarkMessagesAsReadByTripId @Inject constructor(private val repository : MessageRepository) {
    suspend operator fun invoke(tripId: Int): List<MessageModel> = repository.markMessagesAsReadByTripId(tripId)
}