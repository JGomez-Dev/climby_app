package com.example.climby.domain.school

import com.example.climby.data.model.school.SchoolModel
import com.example.climby.data.model.trip.TripModel
import com.example.climby.data.repository.SchoolRepository
import com.example.climby.data.repository.TripRepository
import javax.inject.Inject

class Get @Inject constructor(private val repository : SchoolRepository) {
    suspend operator fun invoke(): List<SchoolModel> = repository.getSchools()
}