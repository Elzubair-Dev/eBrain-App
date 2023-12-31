package com.example.ebrain.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ebrain.core.data.util.PrivacyConverter
import com.example.ebrain.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
@TypeConverters(PrivacyConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}