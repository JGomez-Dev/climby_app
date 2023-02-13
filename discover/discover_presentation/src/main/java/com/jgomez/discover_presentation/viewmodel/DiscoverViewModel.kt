package com.jgomez.discover_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgomez.common_utils.Resource
import com.jgomez.discover_presentation.views.TripState
import com.jgomez.discover_domain.usecase.GetCardsInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val getCardsInformation: GetCardsInformation) : ViewModel() {
    private val _trips = MutableStateFlow(TripState())
    val trips: StateFlow<TripState> = _trips

init {
    getTrips()
}

    fun getTrips() {
        getCardsInformation().onEach {
            when (it) {
                is Resource.Loading -> {
                    _trips.value = TripState(isLoading = true)
                }
                is Resource.Error -> {
                    _trips.value = TripState(error = it.message)
                }
                is Resource.Success -> {
                    _trips.value = TripState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}