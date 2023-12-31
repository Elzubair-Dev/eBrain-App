package com.example.ebrain.feature_settings.edit_password.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebrain.feature_settings.edit_password.presentation.components.ResetPasswordSection
import com.example.ebrain.feature_settings.edit_password.presentation.components.SetPasswordSection

@Composable
fun EditPasswordScreen(
    navController: NavController,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    viewModel: EditPasswordViewModel = hiltViewModel()
) {

    val password = viewModel.getPassword()

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (password.isBlank()) {
                SetPasswordSection(
                    navController = navController,
                    addPassword = viewModel::addPassword,
                    isNotAcceptedPassword = viewModel::isNotAcceptedPassword,
                    acceptedSetPassword = viewModel::isAcceptedSetPassword
                )
            } else {
                ResetPasswordSection(
                    navController = navController,
                    password = password,
                    addPassword = viewModel::addPassword,
                    isNotAcceptedPassword = viewModel::isNotAcceptedPassword,
                    isAcceptedResetPassword = viewModel::isAcceptedResetPassword
                )
            }

        }
    }
}