package com.example.climby.ui.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComingOutingsViewModel @Inject constructor() : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    fun getNextTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            /*val result = getAllTripsUseCase()
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            isLoading.postValue(false)*/
        }
    }
}