package com.app.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.trip.TripModel
import com.app.climby.data.model.user.UserModel
import com.app.climby.domain.trip.GetTripsUser
import com.app.climby.domain.user.Get
import com.app.climby.domain.user.GetByEmail
import com.app.climby.domain.user.Insert
import com.app.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val get: Get, private val getByEmail: GetByEmail, private val insert: Insert, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    val finish = MutableLiveData<Boolean>()
    var result: List<TripModel> = emptyList()
    val exists = MutableLiveData<Boolean>()

    fun getUser(userId: Int) {
        viewModelScope.launch {
            try {
                val result = get(userId)
                if (result.experience != null) {
                    val editor = sharedPref.edit()
                    editor.putInt("id", result!!.id)
                    editor.putFloat("score", result!!.score.toFloat())
                    editor.putString("email", result!!.email)
                    editor.putInt("outputs", result!!.outings)
                    editor.putString("displayName", result!!.name)
                    editor.putString("experience", result!!.experience.toString())
                    editor.putString("phone", result!!.phone)
                    editor.putString("photoUrl", result!!.photo)
                    editor.putInt("ratings", result!!.ratings)
                    editor.putString("token", result!!.token)

                    editor.apply()
                    Commons.userSession = result
                    //okSaveUser.postValue(true)
                } else {
                    //okSaveUser.postValue(false)
                }
            } catch (e: Exception) {
                //postUser(userModel)
            }
        }
    }

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            try {
                val result = getByEmail(email)
                if(result.id != 0){
                    val editor = sharedPref.edit()
                    editor.putInt("id", result!!.id)
                    editor.putFloat("score", result!!.score.toFloat())
                    editor.putString("email", result!!.email)
                    editor.putInt("outputs", result!!.outings)
                    editor.putString("displayName", result!!.name)
                    editor.putString("experience", result!!.experience.toString())
                    editor.putString("phone", result!!.phone)
                    editor.putString("photoUrl", result!!.photo)
                    editor.putInt("ratings", result!!.ratings)
                    editor.putString("token", result!!.token)

                    editor.apply()
                    Commons.userSession = result
                    exists.postValue(true)
                }
            } catch (e: Exception) {
                exists.postValue(false)
            }
        }
    }

    private fun postUser(userModel: UserModel) {
        viewModelScope.launch {
            val result = insert(userModel)
            val editor = sharedPref.edit()
            editor.putInt("id", result.id)
            editor.apply()
            Commons.userSession = result
        }
    }
}