package com.example.ebrain.feature_splash.password.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.feature_splash.password.presentation.components.PasswordSet
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PasswordScreen(
    navController: NavController,
    route: String,
    dataStore: DataStore,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    enableVisitorMode: () -> Unit,
    disableVisitorMode: () -> Unit
) {
    val viewModel = viewModel<PasswordScreenViewModel>()

    val state = viewModel.state

    val password = dataStore.getPasswordValue.collectAsState(initial = null).value
    viewModel.getPassword(password)

    var chameleonState by remember {
        mutableStateOf(false)
    }



    LaunchedEffect(key1 = Unit) {
        dataStore.getChameleonState.collectLatest {
            chameleonState = it
        }
    }

    if (state.counter == 6) {
        LaunchedEffect(key1 = Unit) {

            if (viewModel.isPassed()) {
                dataStore.saveVisitorModeState(false)
                navController.navigate(route = route)
                disableVisitorMode()
            } else {
                if (chameleonState) {
                    dataStore.saveVisitorModeState(true)
                    navController.navigate(route = route)
                    enableVisitorMode()
                } else viewModel.reset()
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = .09f)),
                modifier = Modifier.fillMaxWidth()
            )

            PasswordSet(
                counter = state.counter,
                insertNumber = viewModel::insertNumber,
                deleteNumber = viewModel::deleteNumber
            )
        }
    }
}