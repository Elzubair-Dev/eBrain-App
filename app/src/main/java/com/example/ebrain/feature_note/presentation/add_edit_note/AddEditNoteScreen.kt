package com.example.ebrain.feature_note.presentation.add_edit_note

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.example.ebrain.feature_note.domain.model.Note
import com.example.ebrain.feature_note.presentation.add_edit_note.components.AutoColorDialog
import com.example.ebrain.ui.theme.Dark
import com.example.ebrain.ui.theme.NoteOrange
import com.example.ebrain.ui.theme.Task
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    visitorMode: Boolean,
    noteColor: Int,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val isPublic = viewModel.notePrivacy.value == Privacy.Public
    val privacy = if (isPublic) Privacy.Private else Privacy.Public

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val showDialog =  remember { mutableStateOf(false) }

    val brush = remember {
        Brush.linearGradient(
            colors = listOf(
                Color.Cyan,
                Color.Blue,
                Color.Green,
                Color.Yellow,
                NoteOrange
            )
        )
    }

    val scope = rememberCoroutineScope()

    fun onColorClicked(colorInt: Int){
        scope.launch {
            noteBackgroundAnimatable.animateTo(
                targetValue = Color(colorInt),
                animationSpec = tween(
                    durationMillis = 500
                )
            )
        }
        viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
    }

    if(showDialog.value)
        AutoColorDialog(
            value = "#",
            setShowDialog = {
            showDialog.value = it
        },
            layoutDirection = layoutDirection,
            setValue = {
                onColorClicked(android.graphics.Color.valueOf(android.graphics.Color.parseColor(it)).toArgb())
            })



    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is UiEvent.SaveNote -> {
                    navController.navigate(Screen.NotesScreen.route)
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 8.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (noteColor == -1) stringResource(id = R.string.add) else stringResource(
                            id = R.string.update
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
                                        viewModel.onEvent(AddEditNoteEvent.ChangePrivacy(privacy))
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
                                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                                }
                                .padding(4.dp)
                        )
                    }

                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(noteBackgroundAnimatable.value)
                    .padding(horizontal = 8.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(horizontal = 4.dp)
                ) {
                    items(count = 1) {
                        Note.noteColor.forEach { color ->
                            val colorInt = color.toArgb()
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = 3.dp,
                                        color = if (viewModel.noteColor.value == colorInt) {
                                            Color.Black
                                        } else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        onColorClicked(colorInt)
                                    }
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = brush
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.Black,
                                    shape = CircleShape
                                )
                                .clickable {
                                    showDialog.value = true
                                },
                            contentAlignment = Alignment.Center
                        ){
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Add your own color",
                                tint = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                TransparentHintTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    color = Dark,
                    capitalization = KeyboardCapitalization.Words,
                    onValueChange = { title ->
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))
                    },
                    onFocusChange = { focus ->
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(focus))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                TransparentHintTextField(
                    text = contentState.text,
                    hint = contentState.hint,
                    color = Dark,
                    onValueChange = { content ->
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(content))
                    },
                    onFocusChange = { focus ->
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(focus))
                    },
                    imeAction = ImeAction.Default,
                    isHintVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}