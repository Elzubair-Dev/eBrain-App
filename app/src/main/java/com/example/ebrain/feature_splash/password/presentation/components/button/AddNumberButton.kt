package com.example.ebrain.feature_splash.password.presentation.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddNumberButton(
    number: Int,
    insertNumber: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = .05f))
            .clickable {
                insertNumber(number)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$number",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}