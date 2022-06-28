package com.app.climby.domain.school

import com.app.climby.data.repository.SchoolRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : SchoolRepository) {
    suspend operator fun invoke(school: String): String = repository.postSchool(school)
}