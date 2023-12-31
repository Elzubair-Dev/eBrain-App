package com.example.ebrain.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.onBackground,
    unSelectedColor: Color = MaterialTheme.colorScheme.onBackground,
    onClick:()->Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                // Temp Colors, please edit them
                selectedColor = selectedColor,
                unselectedColor = unSelectedColor
            )
        )
    }
}