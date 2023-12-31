package com.example.ebrain.feature_task.domain.use_cases

import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.repository.TaskRepository

class GetTask(
    val repository: TaskRepository
) {
    suspend operator fun invoke(id: Int): Task?{
        return repository.getTaskById(id)
    }
}