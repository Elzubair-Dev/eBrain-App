package com.example.ebrain.feature_task.presentation.task

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.core.domain.util.Screen
import com.example.ebrain.core.presentation.components.BottomBar
import com.example.ebrain.core.presentation.components.CustomFloatingActionButton
import com.example.ebrain.core.presentation.components.NavigationDrawer
import com.example.ebrain.core.presentation.components.Placeholder
import com.example.ebrain.core.presentation.components.SearchingSection
import com.example.ebrain.core.presentation.components.TopBar
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.util.TasksOrder
import com.example.ebrain.feature_task.presentation.task.components.CurrentDateSection
import com.example.ebrain.feature_task.presentation.task.components.TaskItem
import com.example.ebrain.feature_task.presentation.task.components.TaskOrderSection
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    navController: NavController,
    image: Int,
    checked: Boolean,
    visitorMode: Boolean,
    dataStore: DataStore,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    onCheckedChanged: () -> Unit,
    onImageToggled: () -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    navController.enableOnBackPressed(enabled = false)

    val message = stringResource(id = R.string.deleted)
    val undo = stringResource(id = R.string.undo)

    val state = viewModel.state.value

    var title by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val id = viewModel.lastId


    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    fun onDeleteClick(task: Task) {
        viewModel.onEvent(TasksEvent.DeleteTask(task))
        scope.launch {
            val result =
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = undo,
                    duration = SnackbarDuration.Short
                )

            if (result == SnackbarResult.ActionPerformed) {
                if (task.date > LocalDateTime.now()){
                    viewModel.onEvent(TasksEvent.RestoreTask)
                }else{
                    Toast.makeText(context, "Sorry, This task's alarm is in the past now", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToEnd) {
                scope.launch {
                    dataStore.saveCategoryScreenName(Screen.NotesScreen.route)
                    navController.navigate(Screen.NotesScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                }
            }
            false
        }
    )

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        ModalNavigationDrawer(
            drawerContent = {
                NavigationDrawer(
                    image = image,
                    checked = checked,
                    visitorMode = visitorMode,
                    navController = navController,
                    drawerState = drawerState,
                    onCheckedChanged = onCheckedChanged,
                    onImageToggled = onImageToggled
                )
            },
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen
        ) {
            SwipeToDismiss(state = dismissState, background = {
                MaterialTheme.colorScheme.error
            }, directions = setOf(DismissDirection.StartToEnd), dismissContent = {

                Scaffold(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    topBar = {
                        TopBar(
                            title = stringResource(id = R.string.task),
                            menuDisc = stringResource(id = R.string.menu),
                            addItemDisc = stringResource(id = R.string.add),
                            searchingDisc = "",
                            sortingItemsDisc = stringResource(id = R.string.sorting),
                            onMenuClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            onOrderSectionClick = {
                                viewModel.onEvent(TasksEvent.ToggleOrderSection)
                            },
                            onAddItemClick = {
                                navController.navigate(Screen.AddEditTaskScreen.route + "?taskId=${id}")
                            },
                            onSearchClick = {
                                viewModel.onEvent(TasksEvent.ToggleSearchSection)
                            })
                    },
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            dataStore = dataStore
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        CustomFloatingActionButton(
                            route = Screen.TaskScreen.route,
                            onClick = {}
                        )
                    }
                ) { scaffoldPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        CurrentDateSection(scaffoldPadding)

                        AnimatedVisibility(
                            visible = state.isSearchSectionActive,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            SearchingSection(
                                active = state.isSearchSectionActive,
                                onActiveChange = {
                                    viewModel.onEvent(TasksEvent.DeactivateSearchSection)
                                },
                                onSearchClicked = {
                                    title = it
                                    viewModel.onEvent(
                                        TasksEvent.SearchForTasks(
                                            title,
                                            TasksOrder.Title(OrderType.Descending)
                                        )
                                    )
                                })
                        }
                        AnimatedVisibility(
                            visible = state.isOrderSectionVisible,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            TaskOrderSection(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                title = title,
                                taskOrder = state.taskOrder,
                                onOrderChange = { title , tasksOrder ->
                                    viewModel.onEvent(TasksEvent.Order(title, tasksOrder))
                                }
                            )
                        }

                        //Spacer(modifier = Modifier.padding(vertical = 16.dp))

                        if (state.tasks.isEmpty()) {
                            Placeholder(
                                id = R.drawable.task,
                                text = stringResource(id = R.string.task_is_empty)
                            )
                        } else {
                            Spacer(modifier = Modifier.padding(8.dp))
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 4.dp)
                            ) {
                                items(items = state.tasks) { task: Task ->

                                            TaskItem(
                                                task = task,
                                                visitorMode = visitorMode,
                                                layoutDirection = layoutDirection,
                                                onDeleteClick = {
                                                    onDeleteClick(task)
                                                },
                                                onItemClick = {
                                                    viewModel.onEvent(TasksEvent.DeactivateSearchSection)
                                                    navController.navigate(
                                                        Screen.AddEditTaskScreen.route +
                                                                "?taskId=${task.id}&itExist=${true}"
                                                    )
                                                }
                                            )

                                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                                }
                                items(count = 1) {
                                    Spacer(modifier = Modifier.padding(bottom = scaffoldPadding.calculateBottomPadding()))
                                }
                            }
                        }
                    }
                }
            })
        }
    }
}