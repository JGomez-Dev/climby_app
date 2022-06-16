package com.app.climby.domain.school

import com.app.climby.data.model.user.UserModel
import com.app.climby.data.repository.SchoolRepository
import com.app.climby.data.repository.UserRepository
import javax.inject.Inject

class Insert @Inject constructor(private val repository : SchoolRepository) {

    suspend operator fun invoke(school: String): String = repository.postSchool(school)

}