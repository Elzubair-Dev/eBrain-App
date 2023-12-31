package com.example.ebrain.feature_task.presentation.task

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.use_cases.TaskUseCases
import com.example.ebrain.feature_task.domain.util.TasksOrder
import com.example.ebrain.feature_task.domain.util.notification_alarm.TaskAlarmItems
import com.example.ebrain.feature_task.domain.util.notification_alarm.TaskAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val scheduler: TaskAlarmScheduler,
    private val dataStore: DataStore
) : ViewModel() {

    private var alarmItem: TaskAlarmItems? = null

    private val _state = mutableStateOf(TasksState())
    val state: State<TasksState> = _state

    var lastId = 0

    private var recentlyDeletedTask: Task? = null

    private var getTaskJob: Job? = null
    private var getSearchedTaskJob: Job? = null
    private var getPublicSearchedTaskJob: Job? = null
    private var getPublicTaskJob: Job? = null


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
            getPublicTasks(TasksOrder.Title(OrderType.Descending))
        } else {
            getTasks(TasksOrder.Title(OrderType.Descending))
        }
    }

    @SuppressLint("NewApi")
    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.Order -> {
                if (state.value.taskOrder::class == event.taskOrder::class &&
                    state.value.taskOrder.order == event.taskOrder.order
                ) {
                    return
                }
                if (visitorMode) {
                    if (event.title.isBlank()){
                        getPublicTasks(event.taskOrder)
                    }else{
                        getPublicSearchedTasks(event.title, event.taskOrder)
                    }
                } else {
                    if (event.title.isBlank()){
                        getTasks(event.taskOrder)
                    }else{
                        getSearchedTasks(event.title, event.taskOrder)
                    }
                }
            }

            is TasksEvent.SearchForTasks -> {
                if (event.title.isBlank()){
                    if (visitorMode){
                        getPublicTasks(event.taskOrder)
                    }else{
                        getTasks(event.taskOrder)
                    }

                }else{
                    if (visitorMode){
                        getPublicSearchedTasks(event.title, event.taskOrder)
                    }else{
                        getSearchedTasks(event.title, event.taskOrder)
                    }
                }
            }
            is TasksEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeletedTask = event.task
                }

                alarmItem = TaskAlarmItems(
                    time = event.task.date,
                    title = event.task.title,
                    id = event.task.id!!
                )
                alarmItem?.let (scheduler::cancel)

            }

            is TasksEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.addTask(recentlyDeletedTask ?: return@launch)

                    alarmItem = TaskAlarmItems(
                        time = recentlyDeletedTask!!.date,
                        title = recentlyDeletedTask?.title!!,
                        id = recentlyDeletedTask?.id!!
                    )
                    alarmItem?.let (scheduler::schedule )

                    recentlyDeletedTask = null
                }
            }

            is TasksEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is TasksEvent.ActivateSearchSection -> {
                _state.value = _state.value.copy(
                    isSearchSectionActive = true
                )
            }
            is TasksEvent.DeactivateSearchSection -> {
                _state.value = _state.value.copy(
                    isSearchSectionActive = false
                )
            }
            is TasksEvent.ToggleSearchSection -> {
                _state.value = _state.value.copy(
                    isSearchSectionActive = !_state.value.isSearchSectionActive
                )
            }
        }
    }

    private fun getTasks(taskOrder: TasksOrder) {
        getTaskJob?.cancel()

        getTaskJob = taskUseCases.getTasks(taskOrder)
            .onEach { tasks ->
                _state.value = state.value.copy(
                    tasks = tasks,
                    taskOrder = taskOrder
                )

                lastId = if (tasks.isNotEmpty()) tasks.sortedBy {
                    it.id
                }[tasks.lastIndex].id!! else 0
            }.launchIn(viewModelScope)


    }

    private fun getPublicTasks(tasksOrder: TasksOrder) {
        getPublicTaskJob?.cancel()

        getPublicTaskJob = taskUseCases.getPublicTasks(tasksOrder)
            .onEach { publicTask ->

                _state.value = state.value.copy(
                    tasks = publicTask,
                    taskOrder = tasksOrder
                )

            }.launchIn(viewModelScope)

    }

    private fun getSearchedTasks(title: String, tasksOrder: TasksOrder) {
        getSearchedTaskJob?.cancel()

        getSearchedTaskJob = taskUseCases.getTasksByTitle(title, tasksOrder)
            .onEach { searchedTask ->

                _state.value = state.value.copy(
                    tasks = searchedTask,
                    taskOrder = tasksOrder
                )

            }.launchIn(viewModelScope)

    }

    private fun getPublicSearchedTasks(title: String, tasksOrder: TasksOrder) {
        getPublicSearchedTaskJob?.cancel()

        getPublicSearchedTaskJob = taskUseCases.getPublicTasksByTitle(title, tasksOrder)
            .onEach { searchedTask ->

                _state.value = state.value.copy(
                    tasks = searchedTask,
                    taskOrder = tasksOrder
                )

            }.launchIn(viewModelScope)

    }
}