package com.example.climby.ui.discover.viewmodel

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.trip.GetAllTrips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val getAllTrips: GetAllTrips, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    val isLoading = MutableLiveData<Boolean>()
    lateinit var result: List<TripModel>

    fun getTrips() {
        viewModelScope.launch {
            isLoading.postValue(true)
            result = getAllTrips(sharedPref.getInt("id", 0))
            if (!result.isNullOrEmpty())
                tripsModel.postValue(result.toList())
            isLoading.postValue(false)
        }
    }

    fun getTripsType(type: String) {
        viewModelScope.launch {
            val resultType: MutableList<TripModel> = arrayListOf()
            if (!result.isNullOrEmpty()) {
                when (type) {
                    "Boulder" -> result.forEach { if (it.type?.name == "Boulder") { resultType.add(it) } }
                    "Deportiva" -> result.forEach { if (it.type?.name == "Deportiva") { resultType.add(it) } }
                    "Rocódromo" -> result.forEach { if (it.type?.name == "Rocódromo") { resultType.add(it) } }
                    "Clásica" -> result.forEach { if (it.type?.name == "Clásica") { resultType.add(it) } }
                    "NextWeekend" ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            result.forEach {
                                if ((it.departure?.split(" ")?.get(0) ?: "" == calcNextFriday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSaturday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSunday(LocalDate.now()).toString())) {
                                    resultType.add(it)
                                }
                            }
                        } else {
                            tripsModel.postValue(emptyList())
                        }
                }
            } else {
                tripsModel.postValue(emptyList())
            }
            tripsModel.postValue(resultType.toList())

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

