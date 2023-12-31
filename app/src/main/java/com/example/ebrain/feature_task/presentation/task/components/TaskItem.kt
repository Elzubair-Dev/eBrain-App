package com.example.ebrain.feature_task.presentation.task.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.feature_task.domain.model.Task
import com.example.ebrain.feature_task.domain.util.Priority
import com.example.ebrain.ui.theme.Green
import com.example.ebrain.ui.theme.Red
import com.example.ebrain.ui.theme.Yellow

@ExperimentalMaterial3Api
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskItem(
    task: Task,
    visitorMode: Boolean,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {


    val minutes = if (task.date.minute > 9) "${task.date.minute}" else "0${task.date.minute}"
    val hour = if (task.date.hour > 12) "${task.date.hour - 12}" else "${task.date.hour}"
    val zone = if (task.date.hour > 12) "PM" else "AM"

    val taskDismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToEnd) {
                onDeleteClick()
            }
            false
        }
    )

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        SwipeToDismiss(
            state = taskDismissState,
            background = {
                MaterialTheme.colorScheme.error
            },
            directions = setOf(DismissDirection.StartToEnd),
            dismissContent = {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .clickable {
                    onItemClick()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(85.dp)
                    .clip(RectangleShape)
                    .background(
                        when (task.priority) {
                            Priority.High -> Red
                            Priority.Medium -> Yellow
                            else -> Green
                        }
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "$hour:$minutes",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = zone,
                            fontSize = 5.sp,
                            fontWeight = FontWeight.Thin,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 4.dp, top = 4.dp, end = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!visitorMode) {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.baseline_fingerprint_24
                                ),
                                contentDescription = "Is Private",
                                modifier = Modifier.size(16.dp),
                                tint = if (task.privacy == Privacy.Private) MaterialTheme.colorScheme.onBackground
                                else MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Icon(
                            painter = painterResource(
                                id = R.drawable.baseline_repeat_24
                            ),
                            contentDescription = "Is Repeatable",
                            modifier = Modifier.size(16.dp),
                            tint = if (task.isRepeatable) MaterialTheme.colorScheme.onBackground
                            else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onDeleteClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Task",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

            }
            /**Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            ) {


            Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Row(
            modifier = Modifier
            .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Icon(
            painter = painterResource(
            id = R.drawable.baseline_repeat_24
            ),
            contentDescription = "Is Repeatable",
            modifier = Modifier.size(16.dp),
            tint = if (task.isRepeatable) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
            )
            if (!visitorMode) {
            Icon(
            painter = painterResource(
            id = R.drawable.baseline_fingerprint_24
            ),
            contentDescription = "Is Private",
            modifier = Modifier.size(16.dp),
            tint = if (task.privacy == Privacy.Private) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
            )
            }
            Icon(
            painter = painterResource(
            id = if (task.type == TaskType.Kept) R.drawable.kept else R.drawable.dismissed
            ),
            contentDescription = if (task.type == TaskType.Kept) "Kept" else "Dismissed",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onBackground
            )
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
            modifier = Modifier
            .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Icon(
            painter = painterResource(id = R.drawable.baseline_alarm_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {


            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
            text = "${task.date.monthValue} / ${task.date.dayOfMonth}",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onBackground
            )
            }
            }

            }
            }
            Row(
            modifier = Modifier
            .padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
            ) {
            IconButton(
            onClick = onDeleteClick
            ) {
            Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Task",
            tint = MaterialTheme.colorScheme.onBackground
            )
            }
            }
            }
            }**/
            /**Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            ) {


            Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Row(
            modifier = Modifier
            .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
            ) {
            Icon(
            painter = painterResource(
            id = R.drawable.baseline_repeat_24
            ),
            contentDescription = "Is Repeatable",
            modifier = Modifier.size(16.dp),
            tint = if (task.isRepeatable) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
            )
            if (!visitorMode) {
            Icon(
            painter = painterResource(
            id = R.drawable.baseline_fingerprint_24
            ),
            contentDescription = "Is Private",
            modifier = Modifier.size(16.dp),
            tint = if (task.privacy == Privacy.Private) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
            )
            }
            Icon(
            painter = painterResource(
            id = if (task.type == TaskType.Kept) R.drawable.kept else R.drawable.dismissed
            ),
            contentDescription = if (task.type == TaskType.Kept) "Kept" else "Dismissed",
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onBackground
            )
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Row(
            modifier = Modifier
            .padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Icon(
            painter = painterResource(id = R.drawable.baseline_alarm_24),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {


            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Text(
            text = "${task.date.monthValue} / ${task.date.dayOfMonth}",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onBackground
            )
            }
            }

            }
            }
            Row(
            modifier = Modifier
            .padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
            ) {
            IconButton(
            onClick = onDeleteClick
            ) {
            Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Task",
            tint = MaterialTheme.colorScheme.onBackground
            )
            }
            }
            }
            }**/
        }
    })
    }
}