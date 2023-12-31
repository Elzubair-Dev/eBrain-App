package com.example.ebrain.feature_note.presentation.note

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.domain.util.NoteOrder
import com.example.ebrain.feature_note.presentation.note.components.NoteItem
import com.example.ebrain.feature_note.presentation.note.components.NoteOrderSection
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    navController: NavController,
    image: Int,
    checked: Boolean,
    visitorMode: Boolean,
    dataStore: DataStore,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    onCheckedChanged: () -> Unit,
    onImageToggled: () -> Unit,
    viewModel: NoteViewModel = hiltViewModel()
) {
    navController.enableOnBackPressed(enabled = false)

    val message = stringResource(id = R.string.deleted)
    val undo = stringResource(id = R.string.undo)

    val state = viewModel.state.value

    var title by remember {
        mutableStateOf("")
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    fun onDeleteClick(note: Note) {
        viewModel.onEvent(NoteEvent.DeleteNote(note))
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = undo,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                viewModel.onEvent(NoteEvent.RestoreNote)
            }
        }
    }

    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart) {
                scope.launch {
                    dataStore.saveCategoryScreenName(Screen.TaskScreen.route)
                    navController.navigate(Screen.TaskScreen.route) {
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
                Row(modifier = Modifier.background(MaterialTheme.colorScheme.error)) {}
            }, directions = setOf(DismissDirection.EndToStart), dismissContent = {


                Scaffold(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    topBar = {
                        TopBar(
                            title = stringResource(id = R.string.note),
                            menuDisc = stringResource(id = R.string.menu),
                            addItemDisc = stringResource(id = R.string.add_note),
                            searchingDisc = "",
                            sortingItemsDisc = stringResource(id = R.string.sorting),
                            onMenuClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            onOrderSectionClick = {
                                viewModel.onEvent(NoteEvent.ToggleOrderSection)
                            },
                            onAddItemClick = {
                                navController.navigate(Screen.AddEditNoteScreen.route)
                            },
                            onSearchClick = { viewModel.onEvent(NoteEvent.ToggleSearchSection) })
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
                            route = Screen.NotesScreen.route,
                            onClick = {}
                        )
                    }
                ) { scaffoldPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {

                        Spacer(modifier = Modifier.padding(top = scaffoldPadding.calculateTopPadding()))

                        AnimatedVisibility(
                            visible = state.isSearchSectionActive,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            SearchingSection(
                                active = state.isSearchSectionActive,
                                onActiveChange = {
                                    viewModel.onEvent(NoteEvent.DeactivateSearchSection)
                                },
                                onSearchClicked = {
                                    title = it
                                    viewModel.onEvent(
                                        NoteEvent.SearchForNotes(
                                            title,
                                            NoteOrder.Date(OrderType.Descending)
                                        )
                                    )
                                })
                        }
                        AnimatedVisibility(
                            visible = state.isOrderSectionVisible,
                            enter = fadeIn() + slideInVertically(),
                            exit = fadeOut() + slideOutVertically()
                        ) {
                            NoteOrderSection(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                noteOrder = state.noteOrder,
                                title = title,
                                onOrderChange = { title, noteOrder ->
                                    viewModel.onEvent(NoteEvent.Order(title, noteOrder))
                                }
                            )
                        }

                        if (state.notes.isEmpty()) {
                            Placeholder(
                                id = R.drawable.note,
                                text = stringResource(id = R.string.note_is_empty)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 8.dp)
                            ) {
                                items(items = state.notes) { note: Note ->
                                    NoteItem(
                                        note = note,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.onEvent(NoteEvent.DeactivateSearchSection)
                                                navController.navigate(
                                                    Screen.AddEditNoteScreen.route +
                                                            "?noteId=${note.id}&noteColor=${note.color}"
                                                )
                                            },
                                        layoutDirection = layoutDirection,
                                        onDeleteClick = {
                                            onDeleteClick(note)
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