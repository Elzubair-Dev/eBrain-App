package com.example.ebrain.feature_note.presentation.note.components

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
import com.example.ebrain.feature_note.domain.util.NoteOrder

@Composable
fun NoteOrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    title: String = "",
    onOrderChange: (String, NoteOrder) -> Unit
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
                selected = noteOrder is NoteOrder.Title,
                onClick = { onOrderChange(title, NoteOrder.Title(noteOrder.orderType)) }
            )

            CustomRadioButton(
                text = stringResource(id = R.string.date),
                selected = noteOrder is NoteOrder.Date,
                onClick = { onOrderChange(title, NoteOrder.Date(noteOrder.orderType)) }
            )

            CustomRadioButton(
                text = stringResource(id = R.string.color),
                selected = noteOrder is NoteOrder.Color,
                onClick = { onOrderChange(title, NoteOrder.Color(noteOrder.orderType)) }
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
                selected = noteOrder.orderType is OrderType.Ascending,
                onClick = { onOrderChange(title, noteOrder.copy(OrderType.Ascending)) }
            )

            CustomRadioButton(
                text = stringResource(id = R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending,
                onClick = { onOrderChange(title, noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}