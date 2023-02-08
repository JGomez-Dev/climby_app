package com.app.climby.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.domain.booking.GetNotificationByUserId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject  constructor(val getNotificacion : GetNotificationByUserId) : ViewModel() {

    val exitsNotification = MutableLiveData<Int>()

    fun getNotification(userEmail: String) {
        viewModelScope.launch {
            try {
                val result = getNotificacion(userEmail)
                exitsNotification.postValue(result)
            } catch (e: Exception) {
                exitsNotification.postValue(0)
            }
        }
    }
}