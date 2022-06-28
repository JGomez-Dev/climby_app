package com.app.climby.domain.booking

import com.app.climby.data.repository.BookingRepository
import javax.inject.Inject

class GetNotificationByUserId  @Inject constructor(private val repository : BookingRepository) {
    suspend operator fun invoke(userId: Int): Int = repository.getNotificationByUserId(userId)
}