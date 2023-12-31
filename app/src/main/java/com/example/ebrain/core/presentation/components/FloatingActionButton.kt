package com.example.ebrain.core.presentation.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ebrain.R

@Composable
fun CustomFloatingActionButton(
    route: String,
    onClick:(String)->Unit
) {
    val context = LocalContext.current
    FloatingActionButton(
        modifier = Modifier
            .offset(y = 50.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                shape = CircleShape
            ),
        shape = CircleShape,
        onClick = {
            onClick(route)
            Toast.makeText(context, "Syncing is coming soon", Toast.LENGTH_SHORT).show()
                  },
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(defaultElevation = 0.dp),
        containerColor = MaterialTheme.colorScheme.tertiary
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_sync_24),
            contentDescription = "Syncing App",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}