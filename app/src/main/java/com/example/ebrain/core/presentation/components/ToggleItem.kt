package com.example.ebrain.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebrain.R

@Composable
fun ToggleItem(
    iconID: Int,
    text: String,
    iconBackground: Color = MaterialTheme.colorScheme.onBackground,
    color: Color = MaterialTheme.colorScheme.onBackground,
    image: Int = R.drawable.usa,
    onImageToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onImageToggle()
            }
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .background(iconBackground)
                    .padding(2.dp)
                , contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconID),
                    contentDescription = text,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Image(
            painter = painterResource(id = image),
            contentDescription = "toggled image",
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp)
                .clickable {
                    onImageToggle()
                }
                .padding(10.dp)
        )
    }
}