package com.example.ebrain.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.Screen
import com.example.ebrain.ui.theme.Brown
import com.example.ebrain.ui.theme.Cyan
import com.example.ebrain.ui.theme.Dark
import com.example.ebrain.ui.theme.LightGreen
import com.example.ebrain.ui.theme.LightRed
import com.example.ebrain.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    image: Int,
    checked: Boolean,
    visitorMode: Boolean,
    navController: NavController,
    drawerState: DrawerState,
    onCheckedChanged:()->Unit,
    onImageToggled:()->Unit
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(.8f)
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onCheckedChanged()
                }
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .background(Cyan)
                        .padding(2.dp)
                    , contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (checked) R.drawable.baseline_brightness_3_24
                            else R.drawable.baseline_wb_sunny_24
                        ),
                        contentDescription = "Theme",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                Text(
                    text = stringResource(id = R.string.theme),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChanged() },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = Dark,
                    uncheckedTrackColor = White,
                    uncheckedBorderColor = Dark,
                    checkedTrackColor = Dark,
                    checkedThumbColor = White,
                    checkedBorderColor = White
                )
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        if (!visitorMode) {
            NavDrawerItem(iconID = R.drawable.baseline_settings_24,
                title = stringResource(id = R.string.settings),
                contentDescription = "Settings page",
                iconBackground = LightGreen,
                onItemClicked = {
                    scope.launch {
                        val child =
                            launch { navController.navigate(Screen.SettingsScreen.route) }
                        child.join()
                        launch {
                            delay(400)
                            drawerState.close()
                        }
                    }
                })

            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }

        NavDrawerItem(
            iconID = R.drawable.outline_info_24,
            title = stringResource(id = R.string.info),
            contentDescription = "Info page",
            iconBackground = LightRed,
            onItemClicked = {
                scope.launch {
                    val child =
                        launch { navController.navigate(Screen.InfoScreen.route) }
                    child.join()
                    launch {
                        delay(400)
                        drawerState.close()
                    }
                }
            })

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        ToggleItem(
            iconID = R.drawable.baseline_language_24,
            text = stringResource(id = R.string.language),
            image = image,
            iconBackground = Brown,
            onImageToggle = onImageToggled
        )
    }
}