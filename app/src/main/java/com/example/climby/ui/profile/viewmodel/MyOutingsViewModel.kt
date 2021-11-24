package com.example.climby.ui.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyOutingsViewModel @Inject constructor(): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    fun getMyTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            /*val result = getAllTripsUseCase()
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            isLoading.postValue(false)*/
        }
    }
}