package com.example.ebrain.feature_splash.password.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebrain.feature_splash.password.presentation.components.button.AddNumberButton

@Composable
fun ButtonsRow(
    number: Int,
    insertNumber:(Int)->Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AddNumberButton(number = number, insertNumber = insertNumber)
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        AddNumberButton(number = number + 1, insertNumber = insertNumber)
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        AddNumberButton(number = number + 2, insertNumber = insertNumber)
    }
}