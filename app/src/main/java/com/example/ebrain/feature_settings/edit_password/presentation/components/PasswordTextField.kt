package com.example.ebrain.feature_settings.edit_password.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.ebrain.R

@Composable
fun PasswordTextField(
    password: String,
    label: Int,
    placeholder: Int,
    isVisible: Boolean,
    onValueChange: (String) -> Unit,
    isError: () -> Boolean,
    onVisibleIconClick: () -> Unit
) {
    TextField(
        value = password,
        onValueChange = {onValueChange(it)},
        label = {
            Text(
                text = stringResource(id = label),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        isError = isError(),
        maxLines = 1,
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(id = placeholder),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        trailingIcon = {
            val icon = if (isVisible)
                R.drawable.baseline_visibility_24
            else R.drawable.baseline_visibility_off_24

            val description = if (isVisible) "Hide Password" else "Show Password"

            IconButton(
                onClick = {onVisibleIconClick()}
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = description,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.05f
            ),
            cursorColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
            errorContainerColor = MaterialTheme.colorScheme.onError.copy(alpha = 0.1f),
            errorIndicatorColor = MaterialTheme.colorScheme.onError,
            errorCursorColor = MaterialTheme.colorScheme.onError,
        )
    )
}