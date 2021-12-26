package com.example.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.R
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.model.user.UserModel
import com.example.climby.domain.user.Insert
import com.example.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class OnBoardingSecondViewModel @Inject constructor(private val insert: Insert, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var result: UserModel? = null
    var okSaveUser = MutableLiveData<Boolean>()

    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            result = insert(userModel)
            if (result != null) {
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
                editor.apply()
                Commons.userSession = result
                okSaveUser.postValue(true)
            } else {
                okSaveUser.postValue(false)
            }

        }
    }
}