package com.example.climby.data.network.school

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchoolService  @Inject constructor(private val api: SchoolApiClient) {

    private val COUNTRIES = arrayOf(
        "Belgium", "France", "Italy", "Germany", "Spain"
    )

    suspend fun getSchools(): List<String> {
        /*return withContext(Dispatchers.IO) {
            val response = api.getSchools()
            response.body() ?: emptyList()
        }*/
        return COUNTRIES.asList()
    }

    suspend fun postSchool(school: String): String {
        return withContext(Dispatchers.IO) {
            val response = api.postSchool(school)
            response.body()!!
        }
    }



}