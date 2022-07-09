package com.app.climby.ui.discover.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.data.model.province.ProvinceTripsModel
import com.app.climby.domain.province.GetAllProvinces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProvinceViewModel  @Inject constructor(private val getAllProvinces: GetAllProvinces) : ViewModel() {

    var provincesModel = MutableLiveData<List<ProvinceModel>>()

    fun getProvince() {
       /* val resultType: MutableList<ProvinceTripsModel> = arrayListOf()*/
        viewModelScope.launch {
            val result = getAllProvinces()
            /*var conttest = 0
            val resultName: MutableList<String> = arrayListOf()
            result.forEach {
                if (it.name?.split(" ")?.get(0) ?: "" != "Elige")
                    resultType.add(ProvinceTripsModel(it.name, conttest))
                conttest = (0..30).random()
            }*/
            if (!result.isNullOrEmpty())
                provincesModel.postValue(result.drop(1))
        }
    }

}