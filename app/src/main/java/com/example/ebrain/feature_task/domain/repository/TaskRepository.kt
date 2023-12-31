package com.example.ebrain.feature_task.domain.repository

import com.example.ebrain.feature_task.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>

    fun getPublicTasks(): Flow<List<Task>>

    fun getTasksByTitle(title: String): Flow<List<Task>>

    fun getPublicTasksByTitle(title: String): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)
}