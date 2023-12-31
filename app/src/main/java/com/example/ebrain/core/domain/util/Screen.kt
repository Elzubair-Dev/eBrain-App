package com.example.ebrain.core.domain.util

import com.example.ebrain.R

sealed class Screen(
    val route: String,
    val icon: Int?
){
    object SplashScreen: Screen("splash_screen", null)
    object InfoScreen: Screen("info_screen", null)
    object SettingsScreen: Screen("settings_screen", null)
    object EditPasswordScreen: Screen("edit_password_screen", null)
    object NotesScreen: Screen("notes_screen", R.drawable.note)
    object AddEditNoteScreen: Screen("add_edit_note_screen", null)
    object TaskScreen: Screen("tasks_screen", R.drawable.task)
    object AddEditTaskScreen: Screen("add_edit_task_screen", null)
}
