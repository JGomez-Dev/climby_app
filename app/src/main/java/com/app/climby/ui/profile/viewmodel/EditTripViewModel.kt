package com.app.climby.ui.profile.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.province.GetAllProvinces
import com.app.climby.domain.trip.DeleteTrip
import com.app.climby.domain.trip.PutTrip
import com.app.climby.domain.type.Get
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTripViewModel  @Inject constructor(private val getAllProvinces: GetAllProvinces, private val delete: DeleteTrip, private val getAllTypes: Get, private val putTrip: PutTrip) : ViewModel() {

    var provincesModel = MutableLiveData<List<String>>()
    var typesModel = MutableLiveData<List<String>>()

    fun deleteTrip(tripModel: TripModel) {
        viewModelScope.launch {
            try {
                delete(tripModel.id)
            }catch (e:Exception){}


        }
    }

    fun updateTrip(tripModel: TripModel) {
        viewModelScope.launch {
            val result: TripModel = putTrip(tripModel)
        }
    }

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