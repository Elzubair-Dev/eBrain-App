package com.example.ebrain.core.domain.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.ui.platform.LocalConfiguration
import java.util.Locale

class Language(
    private val context: Context
) {
    //----changing app Language to Arabic or English according to the value of the parameter named code----//
    fun updateLanguage(code : String){
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration : Configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }


}