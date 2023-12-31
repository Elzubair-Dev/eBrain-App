package com.example.ebrain.feature_task.presentation.add_edit_task

sealed class TaskUiEvent{
    data class ShowSnackbar(val message: String): TaskUiEvent()
    object SaveTask: TaskUiEvent()
}
