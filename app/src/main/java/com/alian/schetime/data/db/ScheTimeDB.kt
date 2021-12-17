package com.alian.schetime.data.db

import android.content.Context
import androidx.room.*
import com.alian.schetime.data.dao.AuthDao
import com.alian.schetime.data.dao.NoteDao
import com.alian.schetime.data.dao.UserDao
import com.alian.schetime.data.model.Note
import com.alian.schetime.data.model.User

@Database(
    entities = [User::class, Note::class], version = 1, exportSchema = false
)
abstract class ScheTimeDB : RoomDatabase() {
    abstract fun authDao(): AuthDao
    abstract fun noteDao(): NoteDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: ScheTimeDB? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ScheTimeDB::class.java,
                "schetime"
            ).build()
    }
}