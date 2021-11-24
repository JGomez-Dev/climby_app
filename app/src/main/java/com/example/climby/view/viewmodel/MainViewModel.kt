package com.example.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.user.UserModel
import com.example.climby.domain.user.Get
import com.example.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject  constructor(private val get: Get, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

}