package com.jgomez.discover_presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgomez.common_utils.Resource
import com.jgomez.discover_domain.model.CardInformation
import com.jgomez.discover_domain.model.TripDetail
import com.jgomez.discover_domain.model.User
import com.jgomez.discover_domain.usecase.GetCardsInformation
import com.jgomez.discover_domain.usecase.GetTripDetail
import com.jgomez.discover_domain.usecase.GetUserInformation
import com.jgomez.discover_domain.usecase.SetUserInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getCardsInformation: GetCardsInformation,
    private val getTripDetail: GetTripDetail,
    //private val setUserInformation: SetUserInformation,
    //private val getUserInformation: GetUserInformation
) : ViewModel() {

    private val _trips = MutableStateFlow(TripState())
    val trips: StateFlow<TripState> = _trips

    private val _user = MutableStateFlow(UserState())
    val user: StateFlow<UserState> = _user

    private val _tripDetail = MutableStateFlow(TripDetailState())
    val tripDetail: StateFlow<TripDetailState> = _tripDetail

    init {
        getTrips()
    }
    /*private fun getUserData(){
        getUserInformation().onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = UserState(isLoading = true)
                }
                is Resource.Error -> {
                    _user.value = UserState(error = it.message)
                }
                is Resource.Success -> {
                    _user.value = UserState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }*/

    fun getTypes() = listOf(
        "Todas",
        "Próximo fin de Semana",
        "Boulder",
        "Deportiva",
        "Rocódromo",
        "Clásica",
        "Psicobloc"
    )

    fun getTravelDetail(idTravel: Int) {
        getTripDetail(idTravel).onEach {
            when (it) {
                is Resource.Loading -> {
                    _tripDetail.value = TripDetailState(isLoading = true)
                }

                is Resource.Error -> {
                    _tripDetail.value = TripDetailState(error = it.message)
                }

                is Resource.Success -> {
                    _tripDetail.value = TripDetailState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getTrips() {
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

data class UserState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: User? = null
)

data class TripDetailState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: TripDetail? = null
)

data class TripState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<CardInformation>? = null
)