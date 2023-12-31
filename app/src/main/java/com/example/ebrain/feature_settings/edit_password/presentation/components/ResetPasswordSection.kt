package com.example.ebrain.feature_settings.edit_password.presentation.components

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ebrain.R
import kotlinx.coroutines.launch

@Composable
fun ResetPasswordSection(
    navController: NavController,
    password: String,
    isAcceptedResetPassword: (String, String, String) -> Boolean,
    addPassword: (String) -> Unit,
    isNotAcceptedPassword: (String) -> Boolean
) {

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    var currentPassword by rememberSaveable {
        mutableStateOf("")
    }

    var isCurrentPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var enteredPassword by rememberSaveable {
        mutableStateOf("")
    }

    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var reEnteredPassword by rememberSaveable {
        mutableStateOf("")
    }

    var isConfirmedPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Image(painter = painterResource(id = R.drawable.vector), contentDescription = "")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            PasswordTextField(
                password = currentPassword,
                label = R.string.enter_current_password,
                placeholder = R.string.enter_only_6_digits,
                isVisible = isCurrentPasswordVisible,
                onValueChange = {
                    if (it.length <= 6) {
                        currentPassword = it
                    }
                },
                isError = {
                    currentPassword.length == 6
                            && currentPassword != password
                            || isNotAcceptedPassword(currentPassword)
                },
                onVisibleIconClick = {
                    isCurrentPasswordVisible = !isCurrentPasswordVisible
                })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            PasswordTextField(
                password = enteredPassword,
                label = R.string.enter_password,
                placeholder = R.string.enter_only_6_digits,
                isVisible = isPasswordVisible,
                onValueChange = {
                    if (it.length <= 6) {
                        enteredPassword = it
                    }
                },
                isError = { isNotAcceptedPassword(enteredPassword) },
                onVisibleIconClick = { isPasswordVisible = !isPasswordVisible })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            PasswordTextField(
                password = reEnteredPassword,
                label = R.string.confirm_password,
                placeholder = R.string.re_enter_password,
                isVisible = isConfirmedPasswordVisible,
                onValueChange = {
                    if (it.length <= 6) {
                        reEnteredPassword = it
                    }
                },
                isError = {
                    reEnteredPassword.length == 6
                            && reEnteredPassword != enteredPassword
                            || isNotAcceptedPassword(reEnteredPassword)
                },
                onVisibleIconClick = { isConfirmedPasswordVisible = !isConfirmedPasswordVisible })
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .clickable {}
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.hide),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 4.dp))

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onBackground)
                        .clickable {
                            if (isAcceptedResetPassword(
                                    currentPassword,
                                    enteredPassword,
                                    reEnteredPassword
                                )
                            ) {
                                addPassword(enteredPassword)
                                Toast
                                    .makeText(context, "Done", Toast.LENGTH_SHORT)
                                    .show()
                                scope.launch {
                                    navController.navigateUp()
                                }

                            } else {
                                Toast
                                    .makeText(context, "Not Saved", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        }
    }
}