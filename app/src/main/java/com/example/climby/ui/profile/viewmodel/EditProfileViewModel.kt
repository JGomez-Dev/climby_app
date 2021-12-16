package com.example.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.user.UserModel
import com.example.climby.domain.user.Update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val update: Update, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    private val _textMLD = MutableLiveData<Boolean>()
    val textLD: LiveData<Boolean> = _textMLD

    fun onUsernameTextChanged(text: CharSequence?) {
        _textMLD.value = text.toString().length == 12
    }

    fun updateUser(userModel: UserModel) {
        viewModelScope.launch {
            val result = update(userModel)
            val editor = sharedPref.edit()
            editor.putString("experience", result.experience.toString())
            editor.putString("phone", result.phone)
            editor.putString("photoUrl", result.photo)
            editor.apply()
        }
    }
}