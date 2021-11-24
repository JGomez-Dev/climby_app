package com.example.climby.data.repository

import com.example.climby.data.model.user.UserModel
import com.example.climby.data.network.school.SchoolService
import com.example.climby.data.network.user.UserService
import javax.inject.Inject

class SchoolRepository  @Inject constructor(private val api : SchoolService){

    suspend fun postSchool(school: String) = api.postSchool(school)
    suspend fun getSchools() = api.getSchools()
}