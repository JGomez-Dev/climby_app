package com.example.climby.ui.publish.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.domain.province.GetAllProvinces
import com.example.climby.domain.type.Get
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishViewModel @Inject constructor(private val getAllProvinces: GetAllProvinces, private val getAllTypes: Get) : ViewModel() {

    var provincesModel = MutableLiveData<List<String>>()
    var typesModel = MutableLiveData<List<String>>()
    var siteModel = MutableLiveData<String>()

    fun getProvince(){
        viewModelScope.launch {
            val result = getAllProvinces()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach{
                it.name?.let { it1 -> resultName.add(it1) }
            }
            if (!result.isNullOrEmpty())
                provincesModel.postValue(resultName)
        }
    }

    fun saveSite(site: String){
        viewModelScope.launch {
            siteModel.postValue("Hola")
        }
    }

    fun getTypes(){
        viewModelScope.launch {
            val result = getAllTypes()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach{
                it.name?.let { it1 -> resultName.add(it1) }
            }
            if (!result.isNullOrEmpty())
                typesModel.postValue(resultName)
        }
    }
}