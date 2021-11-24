package com.example.climby.domain.type

import com.example.climby.data.model.types.TypesModel
import com.example.climby.data.repository.TypeRepository
import javax.inject.Inject

class Get  @Inject constructor(private val repository : TypeRepository) {
    suspend operator fun invoke(): List<TypesModel>  = repository.getTypes()
}