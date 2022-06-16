package com.app.climby.view.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OnBoardingFirstViewModel @Inject constructor() : ViewModel() {

    private val _textMLD = MutableLiveData<Boolean>()
    val textLD: LiveData<Boolean> = _textMLD

    fun onUserPhoneTextChanged(text: CharSequence?) {
        _textMLD.value = text.toString().replace(" ","").length == 9
    }

}