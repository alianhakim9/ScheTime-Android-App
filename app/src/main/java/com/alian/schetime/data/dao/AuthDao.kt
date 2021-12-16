package com.alian.schetime.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alian.schetime.data.model.User

@Dao
interface AuthDao {

    @Query("SELECT * FROM users WHERE user_email=:email AND user_password=:password")
    suspend fun signIn(email: String, password: String): User

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun signUp(user: User)

}