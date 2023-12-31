package com.example.ebrain.feature_task.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ebrain.core.data.util.LocalDateTimeConverter
import com.example.ebrain.core.data.util.PrivacyConverter
import com.example.ebrain.feature_task.data.util.IntervalConverter
import com.example.ebrain.feature_task.domain.model.Task

@Database(
    entities = [Task::class],
    version = 1
)
@TypeConverters(PrivacyConverter::class, LocalDateTimeConverter::class, IntervalConverter::class)
abstract class TaskDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object{
        const val DATABASE_NAME = "tasks_db"
    }
}