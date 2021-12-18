package com.alian.schetime.data.repository

import com.alian.schetime.data.db.ScheTimeDB
import com.alian.schetime.data.model.Note

class NoteRepository(
    private val db: ScheTimeDB,
) {

    suspend fun add(note: Note) = db.noteDao().insertNote(note)

    fun get(userId: Int) = db.noteDao().getNote(userId)

    suspend fun update(note: Note) = db.noteDao().updateNote(note)

    suspend fun delete(note: Note) = db.noteDao().deleteNote(note)

}