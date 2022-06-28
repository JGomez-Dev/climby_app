package com.app.climby.ui.publish.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.province.GetAllProvinces
import com.app.climby.domain.trip.Insert
import com.app.climby.domain.type.Get
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishViewModel @Inject constructor(private val getAllProvinces: GetAllProvinces, private val getAllTypes: Get, private val insert: Insert) : ViewModel() {

    var provincesModel = MutableLiveData<List<String>>()
    var typesModel = MutableLiveData<List<String>>()
    var tripCreated = MutableLiveData<Boolean>()

    fun getProvince() {
        viewModelScope.launch {
            val result = getAllProvinces()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach {
                it.name?.let { it1 -> resultName.add(it1) }
            }
            if (!result.isNullOrEmpty())
                provincesModel.postValue(resultName)
        }
    }

    fun saveTrip(tripModel: TripModel) {
        viewModelScope.launch {
            val result: TripModel = insert(tripModel)
            tripCreated.postValue(true)
            /* saveTripOnFireBase(result)*/
        }
    }

    /*private fun saveTripOnFireBase(result: TripModel) {


     }*/

    fun getTypes() {
        viewModelScope.launch {
            val result = getAllTypes()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach {
                it.name?.let { it1 -> resultName.add(it1) }
            }
            if (!result.isNullOrEmpty()) {
                typesModel.postValue(resultName.sortedDescending())
            }
        }
    }
}

