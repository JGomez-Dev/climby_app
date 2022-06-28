package com.app.climby.domain.school

import com.app.climby.data.model.school.SchoolModel
import com.app.climby.data.repository.SchoolRepository
import javax.inject.Inject

class Get @Inject constructor(private val repository : SchoolRepository) {
    suspend operator fun invoke(): List<SchoolModel> = repository.getSchools()
}