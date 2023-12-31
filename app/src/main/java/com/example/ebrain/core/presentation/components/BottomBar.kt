package com.example.ebrain.core.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebrain.core.domain.util.DataStore

@Composable
fun BottomBar(
    navController: NavController,
    dataStore: DataStore
) {
    BottomAppBar(
        modifier = Modifier
            .height(65.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        tonalElevation = 22.dp,
        containerColor = MaterialTheme.colorScheme.onTertiary
    ) {
        BottomNav(
            navController = navController,
            dataStore = dataStore)
    }
}