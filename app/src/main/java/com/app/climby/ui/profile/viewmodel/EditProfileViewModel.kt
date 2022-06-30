package com.app.climby.ui.profile.viewmodel

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.user.UserModel
import com.app.climby.domain.user.Update
import com.app.climby.util.Commons
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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
    val isUploadImage = MutableLiveData<Boolean>()
    private val _uriImage = MutableLiveData<String>()
    val uriImage: LiveData<String> = _uriImage
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    fun onUsernameTextChanged(text: CharSequence?) {
        _textMLD.value = text.toString().length == 12
    }

    fun updateUser(userModel: UserModel) {
        viewModelScope.launch {
            result = update(userModel)
            if (result != null) {
                val editor = sharedPref.edit()
                editor.putString("experience", userModel.experience)
                editor.putString("phone", userModel.phone)
                editor.putString("photoUrl", userModel.photo)
                editor.apply()
                Commons.userSession = result
            }
            isComplete.postValue(true)
        }
    }

    fun uploadImage(data: Uri?) {
        if (data != null) {
            val filePath: StorageReference = storageRef.child("users/" + Commons.userSession?.phone)
            val uploadTask = filePath.putFile(data)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri: String = task.result.toString()
                    isUploadImage.postValue(true)
                    _uriImage.value = downloadUri
                } else {
                    isUploadImage.postValue(false)
                }
            }
        }
    }
}