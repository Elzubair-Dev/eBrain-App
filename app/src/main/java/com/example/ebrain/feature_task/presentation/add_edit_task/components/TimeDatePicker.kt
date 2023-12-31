@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ebrain.feature_task.presentation.add_edit_task.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebrain.R
import com.example.ebrain.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.example.ebrain.feature_task.presentation.add_edit_task.AddEditTaskViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeDatePicker(
    itExist: Boolean,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {

    var isTimeSelected by remember {
        mutableStateOf(false)
    }
    var isDateSelected by remember {
        mutableStateOf(false)
    }
    val calenderState = rememberUseCaseState()
    CalendarDialog(
        state = calenderState,
        selection = CalendarSelection.Date { date ->
            viewModel.onEvent(AddEditTaskEvent.ChangeYear(date.year))
            viewModel.onEvent(AddEditTaskEvent.ChangeMonth(date.monthValue))
            viewModel.onEvent(AddEditTaskEvent.ChangeDay(date.dayOfMonth))
            isDateSelected = true
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
        ),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )

    val clockState = rememberUseCaseState()
    val hours = viewModel.taskHour.value
    val minutes = viewModel.taskMinutes.value
    val adjustedMinutes = if (minutes > 9) "$minutes" else "0$minutes"
    val hour = if (hours > 12) "${hours - 12}" else "$hours"
    val zone = if (hours > 12) "PM" else "AM"
    val year = viewModel.taskYear.value
    val months = viewModel.taskMonth.value
    val days = viewModel.taskDay.value
    ClockDialog(
        state = clockState,
        selection = ClockSelection.HoursMinutes { clockHour, clockMinutes ->
            viewModel.onEvent(AddEditTaskEvent.ChangeHour(clockHour))
            viewModel.onEvent(AddEditTaskEvent.ChangeMinutes(clockMinutes))
            isTimeSelected = true
        },
        config = ClockConfig(
            is24HourFormat = false
        ),
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.time) +":",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = stringResource(id = R.string.date) +":",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Text(
                        text = if (isTimeSelected || itExist) "$hour : $adjustedMinutes  $zone" else "",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = if (isDateSelected || itExist) "$year/ $months/ $days" else "",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        clockState.show()
                    }
                    .padding(4.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = if (isTimeSelected || itExist) stringResource(id = R.string.reset) else stringResource(id = R.string.set),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Box(modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        calenderState.show()
                    }
                    .padding(4.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = if (isDateSelected || itExist) stringResource(id = R.string.reset) else stringResource(id = R.string.set),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}