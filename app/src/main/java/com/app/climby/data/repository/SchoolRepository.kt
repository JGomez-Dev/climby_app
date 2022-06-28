package com.app.climby.data.repository

import com.app.climby.data.network.school.SchoolService
import javax.inject.Inject

class SchoolRepository  @Inject constructor(private val api : SchoolService){
    suspend fun postSchool(school: String) = api.postSchool(school)
    suspend fun getSchools() = api.getSchools()
}