package com.example.ebrain.feature_note.di

import android.app.Application
import androidx.room.Room
import com.example.ebrain.feature_note.data.data_source.NoteDatabase
import com.example.ebrain.feature_note.data.repository.NoteRepoImp
import com.example.ebrain.feature_note.domain.repository.NoteRepository
import com.example.ebrain.feature_note.domain.use_cases.AddNote
import com.example.ebrain.feature_note.domain.use_cases.DeleteNote
import com.example.ebrain.feature_note.domain.use_cases.GetNote
import com.example.ebrain.feature_note.domain.use_cases.GetNotes
import com.example.ebrain.feature_note.domain.use_cases.GetNotesByTitle
import com.example.ebrain.feature_note.domain.use_cases.GetPublicNotes
import com.example.ebrain.feature_note.domain.use_cases.GetPublicNotesByTitle
import com.example.ebrain.feature_note.domain.use_cases.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepoImp(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository),
            getPublicNotes = GetPublicNotes(repository),
            getNotesByTitle = GetNotesByTitle(repository),
            getPublicNotesByTitle = GetPublicNotesByTitle(repository)
        )
    }
}