package com.example.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.domain.user.Get
import com.example.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject  constructor(private val get: Get, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    fun getUser(id: Int) {
        viewModelScope.launch {
            val result = get(id)
            Commons.userSession = result
        }
    }

}