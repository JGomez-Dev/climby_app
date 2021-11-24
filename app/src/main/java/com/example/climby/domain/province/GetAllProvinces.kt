package com.example.climby.domain.province

import com.example.climby.data.model.province.ProvinceModel
import com.example.climby.data.repository.ProvinceRepository
import javax.inject.Inject

class GetAllProvinces @Inject constructor(private val repository : ProvinceRepository) {
    suspend operator fun invoke(): List<ProvinceModel> = repository.getProvinces()
}