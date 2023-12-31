package com.example.ebrain.feature_settings.settings.presentation

sealed class SettingsEvents{
    data class ChangePasswordState(val state: Boolean): SettingsEvents()
    data class ChangeChameleonState(val state: Boolean): SettingsEvents()
}
