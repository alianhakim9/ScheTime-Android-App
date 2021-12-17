package com.alian.schetime.data.repository

import com.alian.schetime.data.db.ScheTimeDB
import com.alian.schetime.data.model.User

class UserRepository(
    private val db: ScheTimeDB,
) {

    suspend fun updateProfile(user: User) = db.userDao().updateProfile(user)
}