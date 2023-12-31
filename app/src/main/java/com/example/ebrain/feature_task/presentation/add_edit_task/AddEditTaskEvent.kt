package com.example.ebrain.feature_task.presentation.add_edit_task

import androidx.compose.ui.focus.FocusState
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.feature_task.domain.util.Interval
import com.example.ebrain.feature_task.domain.util.Priority

sealed class AddEditTaskEvent{
    data class EnteredTitle(val value: String): AddEditTaskEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTaskEvent()
    data class ChangePriority(val value: Priority): AddEditTaskEvent()
    data class ChangeYear(val year: Int): AddEditTaskEvent()
    data class ChangeMonth(val month: Int): AddEditTaskEvent()
    data class ChangeDay(val day: Int): AddEditTaskEvent()
    data class ChangeHour(val hour: Int): AddEditTaskEvent()
    data class ChangeMinutes(val minutes: Int): AddEditTaskEvent()
    data class ChangeInterval(val value: Interval?): AddEditTaskEvent()
    data class ChangeIntervalValue(val interval: String): AddEditTaskEvent()
    data class ChangeRepeatability(val isRepeatable: Boolean): AddEditTaskEvent()
    data class ChangePrivacy(val privacy: Privacy): AddEditTaskEvent()
    object SaveTask: AddEditTaskEvent()
}
