package com.app.climby.view.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.user.UserModel
import com.app.climby.domain.user.Get
import com.app.climby.utils.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject  constructor(private val get: Get, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

}