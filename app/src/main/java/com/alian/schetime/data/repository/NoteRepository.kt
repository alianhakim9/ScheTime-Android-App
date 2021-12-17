package com.alian.schetime.data.repository

import com.alian.schetime.data.db.ScheTimeDB
import com.alian.schetime.data.model.Note

class NoteRepository(
    private val db: ScheTimeDB,
) {

    suspend fun add(note: Note) = db.noteDao().insertNote(note)

    fun get(userId: Int) = db.noteDao().getNote(userId)

}