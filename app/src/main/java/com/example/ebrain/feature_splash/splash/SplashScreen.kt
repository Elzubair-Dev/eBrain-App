package com.example.ebrain.feature_splash.splash

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.Screen
import com.example.ebrain.feature_splash.password.presentation.PasswordScreen
import com.example.ebrain.feature_splash.splash.components.LogoScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController,
    dataStore: DataStore,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
    enableVisitorMode: () -> Unit,
    disableVisitorMode: () -> Unit
) {
    navController.enableOnBackPressed(enabled = false)

    var password by remember {
        mutableStateOf(false)
    }

    var logoVisibility by remember {
        mutableStateOf(true)
    }

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    var route by remember {
        mutableStateOf(Screen.NotesScreen.route)
    }

    LaunchedEffect(key1 = Unit) {
        launch {
            dataStore.getPasswordState.collectLatest {
                password = it
            }
        }
        launch {
            dataStore.getCategoryScreenName.collectLatest {
                route = it
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        delay(1500L)
        if (password) {
            launch {
                logoVisibility = false
            }
            launch {
                passwordVisibility = true
            }

        } else {
            navController.navigate(route = route)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = logoVisibility,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                LogoScreen()
            }

            AnimatedVisibility(
                visible = passwordVisibility,
                enter = fadeIn() + slideInVertically(
                    animationSpec = tween(
                        durationMillis = 500,
                        delayMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ),
                exit = fadeOut() + slideOutVertically()
            ) {
                PasswordScreen(
                    navController = navController,
                    route = route,
                    layoutDirection = layoutDirection,
                    dataStore = dataStore,
                    enableVisitorMode = enableVisitorMode,
                    disableVisitorMode = disableVisitorMode
                )
            }
        }
    }
}