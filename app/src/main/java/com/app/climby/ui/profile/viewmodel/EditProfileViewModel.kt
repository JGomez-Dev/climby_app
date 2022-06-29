package com.app.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.user.UserModel
import com.app.climby.domain.user.Update
import com.app.climby.util.Commons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val update: Update, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    private val _textMLD = MutableLiveData<Boolean>()
    val textLD: LiveData<Boolean> = _textMLD
    var result: UserModel? = null
    val isComplete = MutableLiveData<Boolean>()

    fun onUsernameTextChanged(text: CharSequence?) {
        _textMLD.value = text.toString().length == 12
    }

    fun updateUser(userModel: UserModel) {
        viewModelScope.launch {
            result = update(userModel)
            if(result!=null){
                val editor = sharedPref.edit()
                editor.putString("experience", userModel.experience)
                editor.putString("phone", userModel.phone)
                editor.putString("photoUrl", userModel.photo)
                editor.apply()
                Commons.userSession = result
               /* Commons.userSession?.photo = result?.photo
                Commons.userSession?.phone = result?.phone
                Commons.userSession?.experience = result?.experience*/
            }
            isComplete.postValue(true)
        }
    }
}