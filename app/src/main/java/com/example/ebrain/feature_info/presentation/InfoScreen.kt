package com.example.ebrain.feature_info.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.ebrain.R
import com.example.ebrain.feature_info.presentation.components.InfoCard

@Composable
fun InfoScreen(
    layoutDirection: LayoutDirection = LayoutDirection.Ltr
) {
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.info),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = 1){
                    InfoCard(
                        iconID = R.drawable.chameleon,
                        title = stringResource(id = R.string.chameleon_feature),
                        contentDescription = "Chameleon feature Information",
                        layoutDirection = layoutDirection,
                        explanation = stringResource(id = R.string.chameleon_feature_exp)
                    )
                    InfoCard(
                        iconID = R.drawable.baseline_fingerprint_24,
                        title = stringResource(id = R.string.privacy),
                        contentDescription = "Privacy Information",
                        layoutDirection = layoutDirection,
                        explanation = stringResource(id = R.string.privacy_exp)
                    )

                    InfoCard(
                        iconID = R.drawable.baseline_repeat_24,
                        title = stringResource(id = R.string.repeatable),
                        contentDescription = "Repeatable Information",
                        layoutDirection = layoutDirection,
                        explanation = stringResource(id = R.string.repeatable_exp)
                    )
                }
            }
        }
    }
}