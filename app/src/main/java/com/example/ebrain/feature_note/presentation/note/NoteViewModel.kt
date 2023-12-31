package com.example.ebrain.feature_note.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.domain.use_cases.NoteUseCases
import com.example.ebrain.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val dataStore: DataStore
) : ViewModel() {
    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotes: Job? = null

    private var getSearchedNotes: Job? = null

    private var getPublicSearchedNotes: Job? = null

    private var getPublicNotes: Job? = null

    private var visitorMode: Boolean = false

    init {
        viewModelScope.launch {
            dataStore.getVisitorModeState.collectLatest {
                visitorMode = it
            }
        }
    }

    init {
        if (visitorMode) {
            getPublicNotes(NoteOrder.Date(OrderType.Descending))
        } else {
            getNotes(NoteOrder.Date(OrderType.Descending))
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }
                if (visitorMode) {
                    if (event.title.isBlank()){
                        getPublicNotes(event.noteOrder)
                    }else{
                        getPublicSearchedNotes(event.title, event.noteOrder)
                    }
                } else {
                    if (event.title.isBlank()){
                        getNotes(event.noteOrder)
                    }else{
                        getSearchedNotes(event.title, event.noteOrder)
                    }
                }
            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NoteEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }

            is NoteEvent.SearchForNotes -> {
                if (event.title.isBlank()){
                    if (visitorMode){
                        getPublicNotes(event.noteOrder)
                    }else{
                        getNotes(event.noteOrder)
                    }
                }else{
                    if (visitorMode){
                        getPublicSearchedNotes(event.title, event.noteOrder)
                    }else{
                        getSearchedNotes(event.title, event.noteOrder)
                    }
                }
            }

            is NoteEvent.ActivateSearchSection -> {
                _state.value = _state.value.copy(
                    isSearchSectionActive = true
                )
            }
            is NoteEvent.DeactivateSearchSection -> {
                _state.value = _state.value.copy(
                    isSearchSectionActive = false
                )
            }
            is NoteEvent.ToggleSearchSection -> {
                _state.value = _state.value.copy(
                    isSearchSectionActive = !_state.value.isSearchSectionActive
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotes?.cancel()

        getNotes = noteUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

    private fun getPublicNotes(noteOrder: NoteOrder) {
        getPublicNotes?.cancel()

        getPublicNotes = noteUseCases.getPublicNotes(noteOrder)
            .onEach { publicNote ->
                _state.value = state.value.copy(
                    notes = publicNote,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

    private fun getSearchedNotes(
        title: String,
        noteOrder: NoteOrder
    ) {
        getSearchedNotes?.cancel()

        getSearchedNotes = noteUseCases.getNotesByTitle(title = title, noteOrder = noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

    private fun getPublicSearchedNotes(
        title: String,
        noteOrder: NoteOrder
    ) {
        getPublicSearchedNotes?.cancel()

        getPublicSearchedNotes = noteUseCases.getPublicNotesByTitle(title = title, noteOrder = noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}