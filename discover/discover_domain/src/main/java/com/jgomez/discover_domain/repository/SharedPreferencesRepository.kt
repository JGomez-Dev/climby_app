package com.jgomez.discover_domain.repository

import com.jgomez.discover_domain.model.User


interface SharedPreferencesRepository {
    fun getUser(): User
    fun setUser(user: User)
    fun removeUser()
}