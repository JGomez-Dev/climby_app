package com.example.climby.ui.publish.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climby.data.model.school.SchoolModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.domain.province.GetAllProvinces
import com.example.climby.domain.school.Get
import com.example.climby.domain.school.Insert
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WhatPlaceViewModel @Inject constructor(private val postSchool: Insert, private  val getSchools: Get) : ViewModel() {

    var schoolsModel = MutableLiveData<MutableList<String?>>()

    fun getAllSchools(){
        viewModelScope.launch {
            val result = getSchools()
            val schoolName: MutableList<String?> = arrayListOf()
            if (!result.isNullOrEmpty()) {
                result.forEach {
                    schoolName.add(it.name)
                }
                schoolsModel.postValue(schoolName)
            }
        }
    }

    fun saveSchool(school: String) {
        viewModelScope.launch {
            postSchool(school)
        }
    }
}