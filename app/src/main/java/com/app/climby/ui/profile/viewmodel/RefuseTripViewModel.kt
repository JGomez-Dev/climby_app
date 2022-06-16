package com.app.climby.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.domain.booking.Delete
import com.app.climby.domain.booking.PutBooking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefuseTripViewModel @Inject constructor(private val delete: Delete, private val putBooking: PutBooking): ViewModel() {

    fun deleteBooking(bookingModel: BookingModel?) {
        viewModelScope.launch {
            delete(bookingModel?.id!!)
        }
    }

    fun updateBooking(bookingModel: BookingModel) {
        viewModelScope.launch {
            /*val result = */putBooking(bookingModel)
        }
    }


}