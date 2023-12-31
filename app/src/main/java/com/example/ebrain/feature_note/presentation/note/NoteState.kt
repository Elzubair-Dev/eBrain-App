package com.example.ebrain.feature_note.presentation.note

import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.domain.util.NoteOrder

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isSearchSectionActive: Boolean = false
)
