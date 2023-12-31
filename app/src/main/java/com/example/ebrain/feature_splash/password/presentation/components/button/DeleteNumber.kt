package com.example.ebrain.feature_splash.password.presentation.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ebrain.R

@Composable
fun DeleteNumberButton(
    deleteNumber:()->Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = .05f))
            .clickable { deleteNumber() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.outline_backspace_24
            ),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )
    }
}