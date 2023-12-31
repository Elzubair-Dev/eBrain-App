package com.example.ebrain.feature_task.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ebrain.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE privacy = 'public'")
    fun getPublicTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE title = :title")
    fun getTasksByTitle(title: String): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE title = :title AND privacy = 'public'")
    fun getPublicTasksByTitle(title: String): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}