package com.example.ebrain.feature_task.presentation.add_edit_task.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebrain.R
import com.example.ebrain.ui.theme.Green
import com.example.ebrain.ui.theme.Red
import com.example.ebrain.ui.theme.Yellow

@Composable
fun PriorityMenu(
    expanded: Boolean,
    onHighPriorityClick: () -> Unit,
    onMediumPriorityClick: () -> Unit,
    onLowPriorityClick: () -> Unit
) {
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 60.dp, bottomEnd = 60.dp))
                .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(topStart = 60.dp, bottomEnd = 60.dp)
            )
        ) {
            //----------High----------//
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onHighPriorityClick()
                    }
                    .padding(start = 22.dp)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.high),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(Red)
                )
            }

            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

            //----------Medium----------//
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onMediumPriorityClick()
                    }
                    .padding(horizontal = 4.dp)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.medium),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(Yellow)
                )
            }

            Divider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.onBackground)

            //----------Low----------//
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLowPriorityClick()
                    }
                    .padding(end = 22.dp)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.low),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(Green)
                )
            }
        }
    }
}