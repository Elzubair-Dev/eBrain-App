package com.example.ebrain.core.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.Screen
import kotlinx.coroutines.launch

@Composable
fun BottomNav(
    navController: NavController,
    dataStore: DataStore
) {
    val items = listOf(
        Screen.NotesScreen,
        Screen.TaskScreen
    )
    val scope = rememberCoroutineScope()

    NavigationBar(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .height(50.dp),
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.onTertiary
    ) {
        items.forEach {
            NavigationBarItem(
                selected = false,
                onClick = {
                    it.route.let { route ->

                        scope.launch {
                            dataStore.saveCategoryScreenName(route)
                        }

                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon!!),
                        contentDescription = "",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                })
        }
    }
}