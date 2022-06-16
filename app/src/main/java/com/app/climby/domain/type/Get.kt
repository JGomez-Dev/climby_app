package com.app.climby.domain.type

import com.app.climby.data.model.types.TypesModel
import com.app.climby.data.repository.TypeRepository
import javax.inject.Inject

class Get  @Inject constructor(private val repository : TypeRepository) {
    suspend operator fun invoke(): List<TypesModel>  = repository.getTypes()
}