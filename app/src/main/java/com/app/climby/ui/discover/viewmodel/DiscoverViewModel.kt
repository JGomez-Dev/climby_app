package com.app.climby.ui.discover.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.climby.data.model.booking.BookingModel
import com.app.climby.data.model.trip.TripModel
import com.app.climby.domain.booking.Delete
import com.app.climby.domain.booking.Insert
import com.app.climby.domain.trip.GetAllTrips
import com.app.climby.view.activity.OnBoardingThreeActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*
import javax.annotation.Nullable
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val getAllTrips: GetAllTrips, private val insert: Insert, private val delete: Delete, @Nullable private val sharedPref: SharedPreferences) : ViewModel() {

    var tripsModel = MutableLiveData<List<TripModel>>()
    var isLoading = MutableLiveData<Boolean>()
    var isBadResponse = MutableLiveData<Boolean>()

    /*var provincesModel = MutableLiveData<List<String>>()*/
    /*var provincesModel = MutableLiveData<List<ProvinceTripsModel>>()*/
    var result: List<TripModel>? = null

    fun getTrips(context: Context, province: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            result = getAllTrips()
            if (result == null) {
                isLoading.postValue(false)
                isBadResponse.postValue(true)
            } else {
                if (result!!.isNotEmpty()) {
                    getTripWithoutQualify(context)
                }
                if(province != "")
                    setTripByProvince(province)
                else {
                    tripsModel.postValue(result)
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun saveBooking(bookingModel: BookingModel) {
        viewModelScope.launch {
            val result: BookingModel = insert(bookingModel)
        }
    }

    fun deleteBooking(booking: BookingModel) {
        viewModelScope.launch {
            delete(booking.id)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setTripByProvince(province: String) {
        val resultWithProvince: MutableList<TripModel> = arrayListOf()
        result?.forEach { it ->
            if (it.province?.name.equals(province, ignoreCase = true) && it.departure!! >= SimpleDateFormat("yyyy-MM-dd 01:01:01").format(Date())) {
                resultWithProvince.add(it)
            }
        }
        tripsModel.postValue(resultWithProvince.toList())
        isLoading.postValue(false)
    }

    private fun getTripWithoutQualify(context: Context) {
        result?.forEach { it ->
            it.bookings?.forEach { _it ->
                val email = sharedPref.getString("email", "")
                if (_it.valuationStatus == false && email == _it.passenger?.email && _it.status == true && checkDate(it.departure!!)) {
                    val intent = Intent(context, OnBoardingThreeActivity::class.java).apply {
                        putExtra("trip", it)
                        putExtra("booking", _it)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun checkDate(date: String): Boolean {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val departure =  date.split(" ")[0]
        if (departure < currentDate) {
            return true
        }
        return false
    }

    @SuppressLint("SimpleDateFormat")
    fun getTripsType(type: String, province: String) {
        viewModelScope.launch {
            val resultType: MutableList<TripModel> = arrayListOf()
            if (result == null) {
                isBadResponse.postValue(true)
                isLoading.postValue(false)
            } else {
                if (result!!.isNotEmpty()) {
                    val today = SimpleDateFormat("yyyy-MM-dd 01:01:01").format(Date())
                    when (type) {
                        "Boulder" -> result!!.forEach {
                            if (it.type?.name == "Boulder" && it.province?.name == province && it.departure!! >= today) {
                                resultType.add(it)
                            } else if (it.type?.name == "Boulder" && province == "Elige") {
                                resultType.add(it)
                            }
                        }
                        "Deportiva" -> result!!.forEach {
                            if (it.type?.name == "Deportiva" && it.province?.name == province && it.departure!! >= today) {
                                resultType.add(it)
                            } else if (it.type?.name == "Deportiva" && province == "Elige") {
                                resultType.add(it)
                            }
                        }
                        "Rocódromo" -> result!!.forEach {
                            if (it.type?.name == "Rocódromo" && it.province?.name == province && it.departure!! >= today) {
                                resultType.add(it)
                            } else if (it.type?.name == "Rocódromo" && province == "Elige") {
                                resultType.add(it)
                            }
                        }
                        "Clásica" -> result!!.forEach {
                            if (it.type?.name == "Clásica" && it.province?.name == province && it.departure!! >= today) {
                                resultType.add(it)
                            } else if (it.type?.name == "Clásica" && province == "Elige") {
                                resultType.add(it)
                            }
                        }
                        "NextWeekend" ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                result!!.forEach {it->
                                    if (it.province?.name.toString() == province && ((it.departure?.split(" ")?.get(0) ?: "" == calcNextFriday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSaturday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSunday(LocalDate.now()).toString()))) {
                                        resultType.add(it)
                                        Log.i("provinvia" , it.province?.name.toString())
                                    } else {
                                        if (it.province?.name.toString() == province && ((it.departure?.split(" ")?.get(0) ?: "" == calcNextFriday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSaturday(LocalDate.now()).toString()) || (it.departure?.split(" ")?.get(0) ?: "" == calcNextSunday(LocalDate.now()).toString()))) {
                                            resultType.add(it)
                                            Log.i("provinvia" , it.province?.name.toString())
                                        }
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

    /* fun getProvince() {
         val resultType: MutableList<ProvinceTripsModel> = arrayListOf()
         viewModelScope.launch {
             val result = getAllProvinces()
             var conttest = 0
             val resultName: MutableList<String> = arrayListOf()
             result.forEach {
                 resultType.add(ProvinceTripsModel(it.name, conttest))
                 conttest = (0..30).random()
             }
             if (!result.isNullOrEmpty())
                 provincesModel.postValue(resultType)
         }
     }*/

    /*fun getProvince() {
        viewModelScope.launch {
            val result = getAllProvinces()
            val resultName: MutableList<String> = arrayListOf()
            result.forEach {
                if (!it.name?.equals("Elige tu provincia")!!) it.name.let { it1 -> resultName.add("$it1 (0)") }
                else it.name.let { it1 -> resultName.add(it1) }
            }
            if (!result.isNullOrEmpty())
                provincesModel.postValue(resultName)
        }
    }*/

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

