package com.example.ebrain.feature_note.data.repository

import com.example.ebrain.feature_note.data.data_source.NoteDao
import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepoImp(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override fun getPublicNotes(): Flow<List<Note>> {
        return dao.getPublicNotes()
    }

    override fun getNotesByTitle(title: String): Flow<List<Note>> {
        return dao.getNotesByTitle(title)
    }

    override fun getPublicNotesByTitle(title: String): Flow<List<Note>> {
        return dao.getPublicNotesByTitle(title)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insert(note: Note) {
        dao.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}