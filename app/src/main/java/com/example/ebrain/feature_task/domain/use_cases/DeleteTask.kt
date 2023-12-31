package com.example.ebrain.feature_task.domain.use_cases

import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
){
    suspend operator fun invoke(task: Task){
        repository.deleteTask(task)
    }
}