package com.app.climby.data.network.school

import com.app.climby.data.model.school.SchoolModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchoolService  @Inject constructor(private val api: SchoolApiClient) {

    suspend fun getSchools(): List<SchoolModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getSchools()
            response.body() ?: emptyList()
        }
    }

    suspend fun postSchool(school: String): String {
        return withContext(Dispatchers.IO) {
            val response = api.postSchool(school)
            response.body()!!
        }
    }
}