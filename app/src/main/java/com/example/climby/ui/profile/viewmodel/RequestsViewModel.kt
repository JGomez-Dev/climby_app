package com.example.climby.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.booking.BookingModel
import com.example.climby.domain.booking.PutBooking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(private val putBooking: PutBooking): ViewModel() {

    fun updateBooking(bookingModel: BookingModel) {
        viewModelScope.launch {
            /*val result = */putBooking(bookingModel)
        }
    }

}