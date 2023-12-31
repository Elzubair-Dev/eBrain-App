package com.example.ebrain.feature_task.presentation.add_edit_task

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.Privacy
import com.example.ebrain.core.domain.util.Screen
import com.example.ebrain.core.presentation.components.TransparentHintTextField
import com.example.ebrain.feature_task.domain.util.Priority
import com.example.ebrain.feature_task.presentation.add_edit_task.components.PriorityMenu
import com.example.ebrain.feature_task.presentation.add_edit_task.components.RepeatabilitySection
import com.example.ebrain.feature_task.presentation.add_edit_task.components.TimeDatePicker
import com.example.ebrain.ui.theme.Green
import com.example.ebrain.ui.theme.Red
import com.example.ebrain.ui.theme.Task
import com.example.ebrain.ui.theme.Yellow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@DelicateCoroutinesApi
@SuppressLint("NewApi")
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    itExist: Boolean,
    visitorMode: Boolean,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {

    val titleState = viewModel.taskTitle.value
    val priorityState = viewModel.taskPriority.value
    val repeatabilityState = viewModel.taskRepeatability.value
    val privacyState = viewModel.taskPrivacy.value
    val intervalName = if (repeatabilityState && viewModel.taskInterval.value != null) viewModel.taskInterval.value!!.name else "Interval"
    val intervalValueState = viewModel.taskIntervalValue.value

    val isPublic = privacyState == Privacy.Public
    val privacy = if (isPublic) Privacy.Private else Privacy.Public

    val snackbarHostState = remember {
        SnackbarHostState()
    }


    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TaskUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is TaskUiEvent.SaveTask -> {
                    navController.navigate(Screen.TaskScreen.route)
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (itExist) stringResource(id = R.string.update) else stringResource(
                            id = R.string.add
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!visitorMode) {

                            Icon(
                                painter = painterResource(id = R.drawable.baseline_fingerprint_24),
                                contentDescription = "Privacy",
                                tint = if (isPublic) MaterialTheme.colorScheme.onBackground else Task,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        viewModel.onEvent(AddEditTaskEvent.ChangePrivacy(privacy))
                                    }
                                    .padding(4.dp)
                            )

                            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        }

                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "save",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                                }
                                .padding(4.dp)
                        )
                    }
                }
            }
            /**, bottomBar = {
                Row {
                    Column(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                            .clip(shape = CircleShape)
                            .clickable {
                                viewModel.onEvent(AddEditTaskEvent.ChangeRepeatability(!repeatabilityState))
                            }
                            .padding(vertical = 4.dp, horizontal = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_repeat_24),
                            contentDescription = "",
                            tint = if (repeatabilityState) Task else MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(id = R.string.repeatable),
                            fontSize = 10.sp,
                            color = if (repeatabilityState) Task else MaterialTheme.colorScheme.onBackground
                        )
                    }
                    /**if (!visitorMode) {
                        Column(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.background)
                                .clip(shape = CircleShape)
                                .clickable {
                                    if (isPublic) {
                                        viewModel.onEvent(AddEditTaskEvent.ChangePrivacy(Privacy.Private))
                                    } else {
                                        viewModel.onEvent(AddEditTaskEvent.ChangePrivacy(Privacy.Public))
                                    }
                                }
                                .padding(vertical = 4.dp, horizontal = 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_fingerprint_24),
                                contentDescription = "",
                                tint = if (isPublic) MaterialTheme.colorScheme.onBackground else Task
                            )
                            Text(
                                text = stringResource(id = R.string.privacy),
                                fontSize = 10.sp,
                                color = if (isPublic) MaterialTheme.colorScheme.onBackground else Task
                            )
                        }
                    }**/

                    // kept and dismissed
                    /**Column(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .weight(1f)
                            .background(MaterialTheme.colorScheme.background)
                            .clip(shape = CircleShape)
                            .clickable {
                                if (typeState == TaskType.Dismissed) {
                                    viewModel.onEvent(AddEditTaskEvent.ChangeType(type = TaskType.Kept))
                                } else {
                                    viewModel.onEvent(AddEditTaskEvent.ChangeType(type = TaskType.Dismissed))
                                }
                            }
                            .padding(vertical = 4.dp, horizontal = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = if (typeState == TaskType.Dismissed) R.drawable.dismissed else R.drawable.kept),
                            contentDescription = "",
                            tint = if (typeState == TaskType.Kept) Task else MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            text = stringResource(id = if (typeState == TaskType.Dismissed) R.string.dismissed else R.string.kept),
                            fontSize = 10.sp,
                            color = if (typeState == TaskType.Kept) Task else MaterialTheme.colorScheme.onBackground
                        )
                    }**/
                }
            }**/
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp)
                    .padding(it)
            ) {

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(0.9.dp)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .padding(bottom = 16.dp)
                )

                TransparentHintTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    capitalization = KeyboardCapitalization.Words,
                    onValueChange = { title ->
                        viewModel.onEvent(AddEditTaskEvent.EnteredTitle(title))
                    },
                    onFocusChange = { focusState ->
                        viewModel.onEvent(AddEditTaskEvent.ChangeTitleFocus(focusState))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground
                    ) + MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                //----------------priority-------------------//

                Column {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expanded = !expanded
                            }
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = when (priorityState) {
                                Priority.High -> stringResource(id = R.string.high)
                                Priority.Medium -> stringResource(id = R.string.medium)
                                else -> stringResource(id = R.string.low)
                            },
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Box(
                            modifier = Modifier
                                .size(15.dp)
                                .clip(CircleShape)
                                .background(
                                    when (priorityState) {
                                        Priority.High -> Red
                                        Priority.Medium -> Yellow
                                        else -> Green
                                    }
                                    //if (priorityState == Priority.High) Red else if (priorityState == Priority.Medium) Yellow else Green
                                )
                        )
                    }

                    Spacer(modifier = Modifier.padding(vertical = 6.dp))

                    PriorityMenu(
                        expanded = expanded,
                        onHighPriorityClick = {
                            viewModel.onEvent(AddEditTaskEvent.ChangePriority(Priority.High))
                            expanded = false
                        },
                        onMediumPriorityClick = {
                            viewModel.onEvent(AddEditTaskEvent.ChangePriority(Priority.Medium))
                            expanded = false
                        },
                        onLowPriorityClick = {
                            viewModel.onEvent(AddEditTaskEvent.ChangePriority(Priority.Low))
                            expanded = false
                        })
                }
                TimeDatePicker(
                    itExist = itExist,
                    layoutDirection = layoutDirection
                )

                RepeatabilitySection(
                    text = intervalValueState?.toString() ?: "",
                    repeatabilityState = repeatabilityState,
                    intervalName = intervalName,
                    onRepeatabilityStateChange = {
                        viewModel.onEvent(AddEditTaskEvent.ChangeRepeatability(!repeatabilityState))
                        viewModel.onEvent(AddEditTaskEvent.ChangeInterval(null))
                    }
                )
            }
        }
    }
}