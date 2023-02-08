package com.app.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.trip.GetTripById
import com.app.climby.domain.user.GetByEmail
import com.app.climby.util.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val _getTripById: GetTripById, private val getByEmail: GetByEmail, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    val finish = MutableLiveData<Boolean>()
    var result: List<TripModel> = emptyList()
    val exists = MutableLiveData<Boolean>()
    lateinit var trip: TripModel
    var tripModel = MutableLiveData<TripModel>()

    fun getTripById(idTrip: Int) {
        viewModelScope.launch {
            trip = _getTripById(idTrip)
            tripModel.postValue(trip)
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            try {
                val result = getByEmail(email)
                if(result.email != "") {
                    val editor = sharedPref.edit()
                    editor.putFloat("score", result.score.toFloat())
                    editor.putString("email", result.email)
                    editor.putInt("outputs", result.outings)
                    editor.putString("displayName", result.name)
                    editor.putString("experience", result.experience.toString())
                    editor.putString("phone", result.phone)
                    editor.putString("photoUrl", result.photo)
                    editor.putInt("ratings", result.ratings)
                    editor.putString("token", result.token)
                    editor.apply()
                    Commons.userSession = result
                    exists.postValue(true)
                }
            } catch (e: Exception) {
                exists.postValue(false)
            }
        }
    }
}