package com.alian.schetime.data.dao

import androidx.room.Dao
import androidx.room.Update
import com.alian.schetime.data.model.User

@Dao
interface UserDao {

    @Update
    suspend fun updateProfile(user: User)

}