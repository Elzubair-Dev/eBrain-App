package com.example.ebrain.feature_task.domain.use_cases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.repository.TaskRepository
import com.example.ebrain.feature_task.domain.util.TasksOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPublicTasks(
    private val repository: TaskRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        taskOrder: TasksOrder = TasksOrder.Title(OrderType.Descending)
    ): Flow<List<Task>> {
        return repository.getPublicTasks().map {task ->
            when(taskOrder.order){
                is OrderType.Ascending -> {
                    when(taskOrder){
                        is TasksOrder.Title -> task.sortedBy { it.title.lowercase() }
                        is TasksOrder.Date -> task.sortedBy { it.date.monthValue * 30 + it.date.dayOfYear }
                        is TasksOrder.Priority -> task.sortedBy { it.priority.ordinal }
                    }
                }
                is OrderType.Descending -> {
                    when(taskOrder){
                        is TasksOrder.Title -> task.sortedByDescending { it.title.lowercase() }
                        is TasksOrder.Date -> task.sortedByDescending { it.date.monthValue * 30 + it.date.dayOfYear }
                        is TasksOrder.Priority -> task.sortedByDescending { it.priority.ordinal }
                    }
                }
            }
        }
    }
}