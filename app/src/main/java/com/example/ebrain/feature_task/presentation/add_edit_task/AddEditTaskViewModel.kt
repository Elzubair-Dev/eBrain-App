package com.example.ebrain.feature_task.presentation.add_edit_task

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.core.presentation.components.TransparentTextFieldState
import com.example.ebrain.feature_task.domain.model.InvalidTaskException
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.use_cases.TaskUseCases
import com.example.ebrain.feature_task.domain.util.Interval
import com.example.ebrain.feature_task.domain.util.Priority
import com.example.ebrain.feature_task.domain.util.notification_alarm.TaskAlarmItems
import com.example.ebrain.feature_task.domain.util.notification_alarm.TaskAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val scheduler: TaskAlarmScheduler,
    savedStateHandle: SavedStateHandle
): ViewModel()  {
    private var currentTaskId: Int? = null

    private var alarmItem: TaskAlarmItems? = null

    init {
        savedStateHandle.get<Boolean>("itExist")?.let { itExist ->
            if (itExist){
                savedStateHandle.get<Int>("taskId")?.let { taskId ->
                    viewModelScope.launch {
                        taskUseCases.getTask(taskId)?.also {task->
                            currentTaskId = taskId
                            _taskTitle.value = taskTitle.value.copy(
                                text = task.title,
                                isHintVisible = false
                            )
                            _taskYear.intValue = task.date.year
                            _taskMonth.intValue = task.date.monthValue
                            _taskDay.intValue = task.date.dayOfMonth
                            _taskHour.intValue = task.date.hour
                            _taskMinutes.intValue = task.date.minute
                            _taskIntervalValue.value = task.intervalValue
                            _taskInterval.value = task.interval
                            _taskPrivacy.value = task.privacy
                            _taskRepeatability.value = task.isRepeatable
                            _taskDate.value = task.date
                        }
                    }
                }
            }else{
                savedStateHandle.get<Int>("taskId")?.let { taskId ->
                    currentTaskId = taskId + 1
                }
            }
        }
    }

    private val _taskTitle = mutableStateOf(
        TransparentTextFieldState(
            hint = "Enter title ..."
        )
    )
    val taskTitle: State<TransparentTextFieldState> = _taskTitle

    private val _taskYear = mutableIntStateOf(2024)
    val taskYear: State<Int> = _taskYear

    private val _taskMonth = mutableIntStateOf(1)
    val taskMonth: State<Int> = _taskMonth

    private val _taskDay = mutableIntStateOf(1)
    val taskDay: State<Int> = _taskDay

    private val _taskHour = mutableIntStateOf(1)
    val taskHour: State<Int> = _taskHour

    private val _taskMinutes = mutableIntStateOf(1)
    val taskMinutes: State<Int> = _taskMinutes

    private val _taskInterval = mutableStateOf<Interval?>(null)
    val taskInterval: State<Interval?> = _taskInterval

    private val _taskIntervalValue = mutableStateOf<Long?>(null)
    val taskIntervalValue: State<Long?> = _taskIntervalValue

    private val _taskPriority = mutableStateOf(Priority.Medium)
    val taskPriority: State<Priority> = _taskPriority

    @RequiresApi(Build.VERSION_CODES.O)
    private val _taskDate = mutableStateOf(LocalDateTime.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val taskDate : State<LocalDateTime> = _taskDate

    private val _taskRepeatability = mutableStateOf(false)
    val taskRepeatability: State<Boolean> = _taskRepeatability

    private val _taskPrivacy = mutableStateOf(Privacy.Public)
    val taskPrivacy: State<Privacy> = _taskPrivacy

    private val _eventFlow = MutableSharedFlow<TaskUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    @SuppressLint("NewApi")
    fun onEvent(event: AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.ChangeTitleFocus -> {
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && taskTitle.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.ChangePriority -> {
                _taskPriority.value = event.value
            }
            is AddEditTaskEvent.ChangePrivacy -> {
                _taskPrivacy.value = event.privacy
            }
            is AddEditTaskEvent.ChangeYear -> {
                _taskYear.intValue = event.year
            }
            is AddEditTaskEvent.ChangeMonth -> {
                _taskMonth.intValue = event.month
            }
            is AddEditTaskEvent.ChangeDay -> {
                _taskDay.intValue = event.day
            }
            is AddEditTaskEvent.ChangeHour -> {
                _taskHour.intValue = event.hour
            }
            is AddEditTaskEvent.ChangeMinutes -> {
                _taskMinutes.intValue = event.minutes
            }

            is AddEditTaskEvent.ChangeInterval -> {
                _taskInterval.value = event.value
            }
            is AddEditTaskEvent.ChangeIntervalValue -> {
                if (event.interval.isNotBlank()
                    && event.interval.isDigitsOnly()
                    && event.interval.toLong() > 0){
                    _taskIntervalValue.value = event.interval.toLong()
                }
            }

            is AddEditTaskEvent.ChangeRepeatability -> {
                _taskRepeatability.value = event.isRepeatable
            }

            is AddEditTaskEvent.SaveTask -> {
                val title = taskTitle.value.text
                _taskDate.value = LocalDateTime.of(
                    _taskYear.intValue,
                    _taskMonth.intValue,
                    _taskDay.intValue,
                    _taskHour.intValue,
                    _taskMinutes.intValue

                )
                viewModelScope.launch {
                    try {
                        taskUseCases.addTask(
                            Task(
                                title = if (title.isNotBlank() && title[title.lastIndex].isWhitespace()) title.dropLast(1) else title,
                                priority = taskPriority.value,
                                date = taskDate.value,
                                interval = taskInterval.value,
                                isRepeatable = taskRepeatability.value,
                                privacy = taskPrivacy.value,
                                id = currentTaskId,
                                intervalValue = taskIntervalValue.value
                            )
                        )

                        alarmItem = TaskAlarmItems(
                            time = taskDate.value,
                            intervalValue = when (taskInterval.value){
                                Interval.Year -> {
                                    taskIntervalValue.value!! * 60 * 24 * 30 * 12
                                }
                                Interval.Month -> {
                                    taskIntervalValue.value!! * 60 * 24 * 30
                                }
                                Interval.Week -> {
                                    taskIntervalValue.value!! * 60 * 24 * 7
                                }
                                Interval.Day -> {
                                    taskIntervalValue.value !! * 60 * 24
                                }
                                Interval.Hour -> {
                                    taskIntervalValue.value!! * 60
                                }
                                Interval.Minute -> {
                                    taskIntervalValue.value
                                }

                                else -> {null}
                            },
                            title = taskTitle.value.text,
                            id = currentTaskId!!
                        )
                        if (currentTaskId != null){
                            alarmItem?.let (scheduler::cancel)
                        }
                        alarmItem?.let (scheduler::schedule )

                        _eventFlow.emit(TaskUiEvent.SaveTask)
                    } catch (e: InvalidTaskException){
                        _eventFlow.emit(
                            TaskUiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save task"
                            )
                        )
                    }
                }
            }
        }
    }
}