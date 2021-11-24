package com.example.climby.domain.school

import com.example.climby.data.model.user.UserModel
import com.example.climby.data.repository.SchoolRepository
import com.example.climby.data.repository.UserRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : SchoolRepository) {

    suspend operator fun invoke(school: String): String = repository.postSchool(school)

}