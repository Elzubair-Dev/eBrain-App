package com.example.ebrain.feature_settings.settings.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.Screen
import com.example.ebrain.feature_settings.settings.presentation.components.SwitchItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    dataStore: DataStore,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    val password = viewModel.password.value
    val passwordEnabled = viewModel.passwordEnabled.value
    val chameleonEnabled = viewModel.chameleonEnabled.value

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            bottomBar = {
                if (passwordEnabled) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (password == null) stringResource(
                                id = R.string.set_password
                            )
                            else stringResource(
                                id = R.string.reset_password
                            ),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Thin,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.EditPasswordScreen.route)
                            }
                        )
                    }
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                items(count = 1) {
                    SwitchItem(
                        icon = R.drawable.baseline_vpn_key_24,
                        checked = passwordEnabled,
                        text = stringResource(id = R.string.password),
                        onCheckedChange = {
                            scope.launch {
                                viewModel.onEvent(SettingsEvents.ChangePasswordState(!passwordEnabled))
                                dataStore.savePasswordState(!passwordEnabled)
                            }
                        }
                    )

                    SwitchItem(
                        icon = R.drawable.chameleon,
                        checked = if (passwordEnabled) chameleonEnabled else false,
                        text = stringResource(id = R.string.chameleon_feature),
                        enabled = passwordEnabled,
                        color = if (passwordEnabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSecondary,
                        onCheckedChange = {
                            scope.launch {
                                viewModel.onEvent(SettingsEvents.ChangeChameleonState(!chameleonEnabled))
                                dataStore.saveChameleonState(!chameleonEnabled)
                            }
                        }
                    )
                }
            }
        }
    }
}