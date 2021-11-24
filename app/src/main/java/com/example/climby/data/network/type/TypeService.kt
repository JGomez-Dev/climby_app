package com.example.climby.data.network.type

import com.example.climby.data.model.types.TypesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TypeService @Inject constructor(private val api: TypeApiClient) {

    suspend fun getTypes(): List<TypesModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getTypes()
            response.body() ?: emptyList()
        }
    }
}