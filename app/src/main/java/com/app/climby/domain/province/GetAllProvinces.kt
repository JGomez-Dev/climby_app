package com.app.climby.domain.province

import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.data.repository.ProvinceRepository
import javax.inject.Inject

class GetAllProvinces @Inject constructor(private val repository : ProvinceRepository) {
    suspend operator fun invoke(): List<ProvinceModel> = repository.getProvinces()
}