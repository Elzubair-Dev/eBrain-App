package com.example.ebrain.core.presentation

import androidx.compose.ui.unit.LayoutDirection
import com.example.ebrain.R

data class CoreState(
    var themeChecked: Boolean = false,
    var visitorMode: Boolean = false,
    var code: String = "en",
    var flag: Int = R.drawable.usa,
    var layoutDirection: LayoutDirection = LayoutDirection.Ltr,

)
