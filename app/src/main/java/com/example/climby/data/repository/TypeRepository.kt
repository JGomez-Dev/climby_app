package com.example.climby.data.repository

import com.example.climby.data.network.type.TypeService
import javax.inject.Inject

class TypeRepository  @Inject constructor(private val api : TypeService){
    suspend fun getTypes() = api.getTypes()
}