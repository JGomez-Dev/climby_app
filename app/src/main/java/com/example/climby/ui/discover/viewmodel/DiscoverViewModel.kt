package com.example.climby.ui.discover.viewmodel

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.province.GetAllProvinces
import com.example.climby.domain.trip.GetAllTrips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val getAllTrips: GetAllTrips, private val getAllProvinces: GetAllProvinces, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    var tripModel = MutableLiveData<TripModel>()
    var isLoading = MutableLiveData<Boolean>()
    var isBadResponse = MutableLiveData<Boolean>()
    var provincesModel = MutableLiveData<List<String>>()
    var result: List<TripModel>? = null

    fun getTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            result = getAllTrips()
            if(result == null){
                isLoading.postValue(false)
                isBadResponse.postValue(true)
            }else{
                if (result!!.isNotEmpty())
                    result?.forEach { it ->
                        it.bookings?.forEach { _it ->
                            val id = sharedPref.getInt("id", 0)
                            if(_it.valuationStatus == false && id == _it.passenger?.id){
                                tripModel.postValue(it)
                            }
                        }
                    }
                    tripsModel.postValue(result!!.toList())
                isLoading.postValue(false)
            }
        }
    }

    fun getTripsType(type: String, province: String) {
        viewModelScope.launch {
            val resultType: MutableList<TripModel> = arrayListOf()
            if(result == null){
                isBadResponse.postValue(true)
                isLoading.postValue(false)
            }else {
                if (result!!.isNotEmpty()) {
                    when (type) {
                        "Boulder" -> result!!.forEach {
                            if (it.type?.name == "Boulder" && it.province?.name == province) {
                                resultType.add(it)
                            }
                        }
                        "Deportiva" -> result!!.forEach {
                            if (it.type?.name == "Deportiva" && it.province?.name == province) {
                                resultType.add(it)
                            }
                        }
                        "Rocódromo" -> result!!.forEach {
                            if (it.type?.name == "Rocódromo" && it.province?.name == province) {
                                resultType.add(it)
                            }
                        }
                        "Clásica" -> result!!.forEach {
                            if (it.type?.name == "Clásica" && it.province?.name == province) {
                                resultType.add(it)
                            }
                        }
                        "NextWeekend" ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                result!!.forEach {
                                    if (( it.province?.name == province && (it.departure?.split(" ")?.get(0) ?: "" == calcNextFriday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSaturday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSunday(LocalDate.now()).toString()))) {
                                        resultType.add(it)
                                    }
                                }
                            } else {
                                tripsModel.postValue(emptyList())
                            }
                    }
                    tripsModel.postValue(resultType.toList())
                } else {
                    tripsModel.postValue(emptyList())
                }
            }
        }
    }

    fun getProvince(){
        viewModelScope.launch {
            val result = getAllProvinces()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach{
                it.name?.let { it1 -> resultName.add("$it1 - 0") }
            }
            if (!result.isNullOrEmpty())
                provincesModel.postValue(resultName)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calcNextFriday(d: LocalDate): LocalDate? {
        return d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calcNextSaturday(d: LocalDate): LocalDate? {
        return d.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calcNextSunday(d: LocalDate): LocalDate? {
        return d.with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
    }
}

