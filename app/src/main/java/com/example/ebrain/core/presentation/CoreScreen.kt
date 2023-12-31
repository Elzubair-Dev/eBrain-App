package com.example.ebrain.core.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.Screen
import com.example.ebrain.feature_info.presentation.InfoScreen
import com.example.ebrain.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.ebrain.feature_note.presentation.note.NoteScreen
import com.example.ebrain.feature_settings.edit_password.presentation.EditPasswordScreen
import com.example.ebrain.feature_settings.settings.presentation.SettingsScreen
import com.example.ebrain.feature_splash.splash.SplashScreen
import com.example.ebrain.feature_task.presentation.add_edit_task.AddEditTaskScreen
import com.example.ebrain.feature_task.presentation.task.TasksScreen
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalMaterial3Api
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CoreScreen(
    themeChecked: Boolean,
    visitorMode: Boolean,
    image: Int,
    dataStore: DataStore,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    onThemeCheckedChange: () -> Unit,
    onLanguageImageToggled: () -> Unit,
    enableVisitorMode: () -> Unit,
    disableVisitorMode: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {

        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.SplashScreen.route
        ) {

            composable("splash_screen") {
                SplashScreen(
                    navController = navController,
                    dataStore = dataStore,
                    layoutDirection = layoutDirection,
                    enableVisitorMode = enableVisitorMode,
                    disableVisitorMode = disableVisitorMode
                )
            }

            composable(route = Screen.InfoScreen.route) {
                InfoScreen(
                    layoutDirection = layoutDirection
                )
            }

            composable(route = Screen.SettingsScreen.route) {
                SettingsScreen(
                    navController = navController,
                    dataStore = dataStore,
                    layoutDirection = layoutDirection
                )
            }

            composable(route = Screen.EditPasswordScreen.route) {
                EditPasswordScreen(
                    navController = navController,
                    layoutDirection = layoutDirection
                )
            }

            composable(route = Screen.NotesScreen.route) {
                NoteScreen(
                    navController = navController,
                    image = image,
                    checked = themeChecked,
                    visitorMode = visitorMode,
                    dataStore = dataStore,
                    layoutDirection = layoutDirection,
                    onCheckedChanged = onThemeCheckedChange,
                    onImageToggled = onLanguageImageToggled
                )
            }

            composable(
                route = Screen.AddEditNoteScreen.route +
                        "?noteId={noteId}&noteColor={noteColor}",
                arguments = listOf(
                    navArgument(
                        name = "noteId"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "noteColor"
                    ) {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                val color = it.arguments?.getInt("noteColor") ?: -1

                AddEditNoteScreen(
                    navController = navController,
                    visitorMode = visitorMode,
                    layoutDirection = layoutDirection,
                    noteColor = color
                )
            }

            composable(route = Screen.TaskScreen.route) {
               TasksScreen(
                    navController = navController,
                    image = image,
                    checked = themeChecked,
                    visitorMode = visitorMode,
                    dataStore = dataStore,
                    layoutDirection = layoutDirection,
                    onCheckedChanged = onThemeCheckedChange,
                    onImageToggled = onLanguageImageToggled
                )
            }

            composable(
                route = Screen.AddEditTaskScreen.route +
                        "?taskId={taskId}&itExist={itExist}",
                arguments = listOf(
                    navArgument(
                        name = "taskId"
                    ) {
                        type = NavType.IntType
                        defaultValue = 0
                    },
                    navArgument(
                        name = "itExist"
                    ) {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                )
            ) {

                val itExist = it.arguments?.getBoolean("itExist") ?: false

                AddEditTaskScreen(
                    navController = navController,
                    visitorMode = visitorMode,
                    layoutDirection = layoutDirection,
                    itExist = itExist
                )
            }
        }
    }
}