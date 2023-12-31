package com.example.ebrain.feature_task.presentation.task

import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.util.TasksOrder

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val taskOrder: TasksOrder = TasksOrder.Title(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isSearchSectionActive: Boolean = false
)
