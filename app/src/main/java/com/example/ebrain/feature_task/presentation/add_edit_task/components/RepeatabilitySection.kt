package com.example.ebrain.feature_task.presentation.add_edit_task.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebrain.R
import com.example.ebrain.feature_settings.settings.presentation.components.SwitchItem
import com.example.ebrain.feature_task.domain.util.Interval
import com.example.ebrain.feature_task.presentation.add_edit_task.AddEditTaskEvent
import com.example.ebrain.feature_task.presentation.add_edit_task.AddEditTaskViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@RequiresApi(Build.VERSION_CODES.O)
@DelicateCoroutinesApi
@Composable
fun RepeatabilitySection(
    text: String,
    repeatabilityState: Boolean,
    intervalName: String,
    onRepeatabilityStateChange: () -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    var showIntervals by remember {
        mutableStateOf(false)
    }
    Column {
        SwitchItem(
            icon = R.drawable.baseline_repeat_24,
            checked = repeatabilityState,
            text = stringResource(id = R.string.repeatability), onCheckedChange = {
                onRepeatabilityStateChange()
            })

        AnimatedVisibility(
            visible = repeatabilityState,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(
                        text = "* Enter numeric value in the box below",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.every),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    TextField(
                        value = text,
                        onValueChange = {
                            viewModel.onEvent(AddEditTaskEvent.ChangeIntervalValue(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            cursorColor = MaterialTheme.colorScheme.onBackground,
                            focusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.width(60.dp)
                    )

                    Text(
                        text = intervalName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable {
                            showIntervals = !showIntervals
                        }
                    )

                }
            }
        }
        AnimatedVisibility(
            visible = showIntervals,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .shadow(elevation = 20.dp, shape = RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.End
                ) {

                    LazyColumn(
                        modifier = Modifier
                            .height(80.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Top
                    ) {
                        items(count = 1) {

                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(Interval.Minute))
                                        showIntervals = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = Interval.Minute.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(Interval.Hour))
                                        showIntervals = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = Interval.Hour.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(Interval.Day))
                                        showIntervals = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = Interval.Day.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(Interval.Week))
                                        showIntervals = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = Interval.Week.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(Interval.Month))
                                        showIntervals = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = Interval.Month.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(Interval.Year))
                                        showIntervals = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = Interval.Year.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}