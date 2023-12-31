package com.example.ebrain.feature_task.presentation.task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.OrderType
import com.example.ebrain.core.presentation.components.CustomRadioButton
import com.example.ebrain.feature_task.domain.util.TasksOrder

@Composable
fun TaskOrderSection(
    modifier: Modifier = Modifier,
    taskOrder: TasksOrder = TasksOrder.Title(OrderType.Descending),
    title: String = "",
    onOrderChange: (String, TasksOrder) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomRadioButton(
                text = stringResource(id = R.string.title),
                selected = taskOrder is TasksOrder.Title,
                onClick = { onOrderChange(title, TasksOrder.Title(taskOrder.order)) }
            )

            //Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            CustomRadioButton(
                text = stringResource(id = R.string.date),
                selected = taskOrder is TasksOrder.Date,
                onClick = { onOrderChange(title, TasksOrder.Date(taskOrder.order)) }
            )

            //Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            CustomRadioButton(
                text = stringResource(id = R.string.priority),
                selected = taskOrder is TasksOrder.Priority,
                onClick = { onOrderChange(title, TasksOrder.Priority(taskOrder.order)) }
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomRadioButton(
                text = stringResource(id = R.string.ascending),
                selected = taskOrder.order is OrderType.Ascending,
                onClick = { onOrderChange(title, taskOrder.copy(OrderType.Ascending)) }
            )

            CustomRadioButton(
                text = stringResource(id = R.string.descending),
                selected = taskOrder.order is OrderType.Descending,
                onClick = { onOrderChange(title, taskOrder.copy(OrderType.Descending)) }
            )
        }
    }
}