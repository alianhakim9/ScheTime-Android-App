package com.alian.schetime.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = CASCADE
        )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    val id: Int = 0,
    @ColumnInfo(name = "note_title")
    val title: String,
    @ColumnInfo(name = "note_text")
    val text: String,
    @ColumnInfo(name = "note_time")
    val time: String = "",
    @ColumnInfo(name = "user_id")
    val userId: Int,
) : Serializable
