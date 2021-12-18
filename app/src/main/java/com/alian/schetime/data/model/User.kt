package com.alian.schetime.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "job_title")
    val jobTitle: String = "Job Title",
    @ColumnInfo(name = "user_password")
    val password: String = "",
)