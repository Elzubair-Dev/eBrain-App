package com.example.ebrain.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.core.presentation.components.TransparentTextFieldState
import com.example.ebrain.feature_note.domain.model.InvalidNoteException
import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel@Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.intValue = note.color

                        _notePrivacy.value = note.privacy
                    }
                }
            }
        }
    }


    private val _noteTitle = mutableStateOf(
        TransparentTextFieldState(
            hint = "Add Title"
        )
    )
    val noteTitle: State<TransparentTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        TransparentTextFieldState(
            hint = "Enter some content ..."
        )
    )
    val noteContent: State<TransparentTextFieldState> = _noteContent

    private val _noteColor = mutableIntStateOf(Note.noteColor.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _notePrivacy = mutableStateOf(Privacy.Public)
    val notePrivacy: State<Privacy> = _notePrivacy

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.intValue = event.color
            }
            is AddEditNoteEvent.ChangePrivacy -> {
                _notePrivacy.value = event.privacy
            }
            is AddEditNoteEvent.SaveNote -> {
                val title = noteTitle.value.text
                val content = noteContent.value.text
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = if (title.isNotBlank() && title[title.lastIndex].isWhitespace()) title.dropLast(1) else title,
                                content = if (content.isNotBlank() && content[content.lastIndex].isWhitespace()) content.dropLast(1) else content,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId,
                                privacy = notePrivacy.value
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note ..."
                            )
                        )
                    }
                }
            }
        }
    }
}