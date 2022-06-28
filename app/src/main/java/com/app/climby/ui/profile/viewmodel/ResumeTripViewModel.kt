package com.app.climby.ui.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.message.MessageModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.message.MarkMessagesAsReadByTripId
import com.app.climby.domain.trip.GetTripById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeTripViewModel @Inject constructor(private val _getTripById: GetTripById, private val markMessagesAsReadByTripId: MarkMessagesAsReadByTripId) : ViewModel() {

    var tripModel = MutableLiveData<TripModel>()
    lateinit var result: TripModel
    lateinit var messageList: List<MessageModel>

    fun getTripById(idTrip: Int) {
        viewModelScope.launch {
            result = _getTripById(idTrip)
            tripModel.postValue(result)
        }
    }

    fun markMessagesAsRead(trip: TripModel?) {
        viewModelScope.launch {
            messageList = markMessagesAsReadByTripId(trip?.id!!)
            if (messageList.isNullOrEmpty()) {
                //TODO cuando venga vacio
            } else {
            }
        }
    }
}