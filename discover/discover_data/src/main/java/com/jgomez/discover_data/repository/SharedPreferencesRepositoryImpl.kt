package com.jgomez.discover_data.repository

import android.content.Context
import com.jgomez.discover_domain.model.User
import com.jgomez.discover_domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(context: Context) : SharedPreferencesRepository {

    private companion object {
        const val fileName = "PREFERENCE_FILE_KEY"
    }

    private val prefs = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

     override fun getUser(): User {

          val fullName = prefs.getString("fullName", "") ?: ""
          val experience = prefs.getString("experience", "") ?: ""
          val phone = prefs.getString("phone", "") ?: ""
          val email = prefs.getString("email", "") ?: ""
          val score = prefs.getFloat("score", 2f).toDouble()
          val ratings = prefs.getInt("ratings", 0)
          val outputs = prefs.getInt("outputs", 0)
          val userPhoto = prefs.getString("userPhoto", "") ?: ""
          val token = prefs.getString("token", "") ?: ""

          return User(
              email,
              experience,
              fullName,
              outputs,
              phone,
              ratings,
              score,
              userPhoto
          )
      }

    override fun setUser(user: User) {
        prefs.edit().putString("username", user.fullName).apply()
        prefs.edit().putString("experience", user.experience).apply()
        prefs.edit().putString("phone", user.phone).apply()
        prefs.edit().putString("email", user.email).apply()
        prefs.edit().putFloat("score", user.score.toFloat()).apply()
        prefs.edit().putInt("ratings", user.ratings).apply()
        prefs.edit().putInt("outputs", user.outputs).apply()
        prefs.edit().putString("userPhoto", user.userPhoto).apply()
    }

    override fun removeUser() {
        prefs.edit().clear().apply()
    }
}