package com.example.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.user.UserModel
import com.example.climby.domain.user.Get
import com.example.climby.domain.user.Insert
import com.example.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject  constructor(private val get: Get, private val insert: Insert, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var isCharget = MutableLiveData<Boolean>()

    fun getUser(userModel: UserModel) {
        viewModelScope.launch {
            try {
                val result = get(userModel.id)
               /* Commons.userSession = result
                isCharget.postValue(true)*/
            }catch (e: Exception){
                postUser(userModel)
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