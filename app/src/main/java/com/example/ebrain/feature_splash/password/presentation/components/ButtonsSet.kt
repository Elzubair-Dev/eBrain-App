package com.example.ebrain.feature_splash.password.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.ebrain.feature_splash.password.presentation.components.button.AddNumberButton
import com.example.ebrain.feature_splash.password.presentation.components.button.DeleteNumberButton

@Composable
fun ButtonsSet(
    insertNumber: (Int)->Unit,
    deleteNumber:()->Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ButtonsRow(number = 1, insertNumber = insertNumber)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsRow(number = 4, insertNumber = insertNumber)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsRow(number = 7, insertNumber = insertNumber)
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = CircleShape)
            )
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            AddNumberButton(number = 0, insertNumber = insertNumber)
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            DeleteNumberButton(deleteNumber = deleteNumber)

        }
    }
}