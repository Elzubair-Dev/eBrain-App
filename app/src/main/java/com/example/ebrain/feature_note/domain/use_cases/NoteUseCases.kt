package com.example.ebrain.feature_note.domain.use_cases

data class NoteUseCases(
    val getNotes: GetNotes,
    val getPublicNotes: GetPublicNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote,
    val getNotesByTitle: GetNotesByTitle,
    val getPublicNotesByTitle: GetPublicNotesByTitle
)
