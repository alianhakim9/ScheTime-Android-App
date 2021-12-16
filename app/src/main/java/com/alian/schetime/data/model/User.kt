package com.alian.schetime.data.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(
    tableName = "users",
    indices = [Index(value = ["user_email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id: Int = 0,
    @ColumnInfo(name = "user_name")
    val name: String = "",
    @ColumnInfo(name = "user_email")
    val email: String,
    @ColumnInfo(name = "user_password")
    val password: String = "",
)