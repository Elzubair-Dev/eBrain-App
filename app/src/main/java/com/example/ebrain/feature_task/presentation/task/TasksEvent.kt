package com.example.ebrain.feature_task.presentation.task

import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.util.TasksOrder

sealed class TasksEvent {
    data class Order(val title: String, val taskOrder: TasksOrder): TasksEvent()
    data class DeleteTask(val task: Task): TasksEvent()
    data class SearchForTasks(val title: String, val taskOrder: TasksOrder): TasksEvent()
    object RestoreTask: TasksEvent()
    object ToggleOrderSection:TasksEvent()
    object ToggleSearchSection:TasksEvent()
    object ActivateSearchSection:TasksEvent()
    object DeactivateSearchSection:TasksEvent()
}