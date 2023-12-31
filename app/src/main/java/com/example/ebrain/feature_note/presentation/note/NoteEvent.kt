package com.example.ebrain.feature_note.presentation.note

import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.domain.util.NoteOrder

sealed class NoteEvent{
    data class Order(val title: String, val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    data class SearchForNotes(val title: String, val noteOrder: NoteOrder): NoteEvent()
    object RestoreNote: NoteEvent()
    object ToggleOrderSection: NoteEvent()
    object ToggleSearchSection: NoteEvent()
    object ActivateSearchSection: NoteEvent()
    object DeactivateSearchSection: NoteEvent()
}
