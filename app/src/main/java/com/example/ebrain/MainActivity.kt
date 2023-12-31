package com.example.ebrain

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.Language
import com.example.ebrain.core.presentation.CoreScreen
import com.example.ebrain.core.presentation.CoreViewModel
import com.example.ebrain.ui.theme.EBrainTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalMaterial3Api
@DelicateCoroutinesApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = viewModel<CoreViewModel>()
            val state = viewModel.state

            val languageManager = Language(this@MainActivity)

            languageManager.updateLanguage(state.value.code)


            EBrainTheme(darkTheme = state.value.themeChecked) {

                val config = LocalConfiguration.current
                val portraitMode = remember {
                    mutableIntStateOf(config.orientation)
                }
                if (portraitMode.intValue == Configuration.ORIENTATION_PORTRAIT) {
                    CoreScreen(
                        themeChecked = state.value.themeChecked,
                        visitorMode = state.value.visitorMode,
                        dataStore = DataStore(this@MainActivity),
                        image = state.value.flag,
                        layoutDirection = state.value.layoutDirection,
                        onThemeCheckedChange = viewModel::onThemeCheckedChange,
                        onLanguageImageToggled = viewModel::onLanguageImageToggled,
                        enableVisitorMode = viewModel::enableVisitorMode,
                        disableVisitorMode = viewModel::disableVisitorMode
                    )
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "... Landscape mode is coming soon ...",
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onBackground
                            )
                    }
                }
            }
        }
    }
}