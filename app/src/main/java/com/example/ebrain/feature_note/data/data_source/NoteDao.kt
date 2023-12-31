package com.example.ebrain.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ebrain.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE privacy = 'public'")
    fun getPublicNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE title = :title")
    fun getNotesByTitle(title: String): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE title = :title AND privacy = 'public'")
    fun getPublicNotesByTitle(title: String): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id:Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}