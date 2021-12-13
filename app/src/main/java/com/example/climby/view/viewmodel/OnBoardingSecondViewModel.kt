package com.example.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.R
import com.example.climby.data.model.user.UserModel
import com.example.climby.domain.user.Insert
import com.example.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class OnBoardingSecondViewModel @Inject constructor(private val insert: Insert, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            val result = insert(userModel)
            val editor = sharedPref.edit()
            editor.putInt("id", result.id)
            editor.putFloat("score", result.score.toFloat())
            editor.putString("email", result.email)
            editor.putInt("outputs", result.outings)
            editor.putString("displayName", result.name)
            editor.putString("experience", result.experience.toString())
            editor.putString("phone", result.phone)
            editor.putString("photoUrl", result.photo)
            editor.apply()
            Commons.userSession = result
        }
    }
}