package com.alian.schetime.data.repository

import com.alian.schetime.data.db.ScheTimeDB
import com.alian.schetime.data.model.User

class AuthRepository(
    private val db: ScheTimeDB
) {
    suspend fun signIn(email: String, password: String) = db.authDao().signIn(email, password)
    suspend fun signUp(user: User) = db.authDao().signUp(user)
}