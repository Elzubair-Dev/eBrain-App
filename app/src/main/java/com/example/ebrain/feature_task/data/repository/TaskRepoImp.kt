package com.example.ebrain.feature_task.data.repository

import com.example.ebrain.feature_task.data.data_source.TaskDao
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepoImp(
    private val dao: TaskDao
): TaskRepository {
    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override fun getPublicTasks(): Flow<List<Task>> {
        return dao.getPublicTasks()
    }

    override fun getTasksByTitle(title: String): Flow<List<Task>> {
        return dao.getTasksByTitle(title)
    }

    override fun getPublicTasksByTitle(title: String): Flow<List<Task>> {
        return dao.getPublicTasksByTitle(title)
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}