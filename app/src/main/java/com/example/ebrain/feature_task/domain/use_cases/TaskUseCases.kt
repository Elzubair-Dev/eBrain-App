package com.example.ebrain.feature_task.domain.use_cases

data class TaskUseCases(
    val getTasks: GetTasks,
    val deleteTask: DeleteTask,
    val addTask: AddTask,
    val getTask: GetTask,
    val getPublicTasks: GetPublicTasks,
    val getTasksByTitle: GetTasksByTitle,
    val getPublicTasksByTitle: GetPublicTaskByTitle
)
