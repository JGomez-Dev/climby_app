package com.app.climby.view.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.domain.user.Insert
import com.app.climby.util.Commons
import com.app.climby.util.UserExperience
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class OnBoardingSecondViewModel @Inject constructor(private val insert: Insert/*, @Nullable private val sharedPref: SharedPreferences*/) :
    ViewModel() {

    var result: UserModel? = null
    var okSaveUser = MutableLiveData<Boolean>()
    var token = MutableLiveData<String>()

    private var TAG: String = "PushNotifiacion"

    fun saveUser(userModel: UserModel) {

        viewModelScope.launch {
            result = insert(userModel)
            if (result != null) {
                /*val editor = sharedPref.edit()
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
                okSaveUser.postValue(true)*/
            } else {
                okSaveUser.postValue(false)
            }
        }
    }

    fun generateToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            token.postValue(task.result)
            Log.i(TAG, token.toString())
        })
    }
}