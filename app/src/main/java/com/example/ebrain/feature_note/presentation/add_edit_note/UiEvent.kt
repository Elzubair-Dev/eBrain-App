package com.example.ebrain.feature_note.presentation.add_edit_note

sealed class UiEvent{
    data class ShowSnackbar(val message: String):UiEvent()
    object SaveNote: UiEvent()
}
