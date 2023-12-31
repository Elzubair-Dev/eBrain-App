package com.example.ebrain.feature_settings.settings.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebrain.core.domain.util.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore
): ViewModel() {

    private val _password = mutableStateOf<String?>(null)
    var password : State<String?> = _password

    private val _passwordEnabled = mutableStateOf(false)
    var passwordEnabled : State<Boolean> = _passwordEnabled

    private val _chameleonEnabled = mutableStateOf(false)
    var chameleonEnabled : State<Boolean> = _chameleonEnabled

    init {
        viewModelScope.launch {
            launch {
                dataStore.getPasswordValue.collectLatest {
                    _password.value = it
                }
            }
            launch {
                dataStore.getPasswordState.collectLatest {
                    _passwordEnabled.value = it
                }
            }
            launch {
                dataStore.getChameleonState.collectLatest {
                    _chameleonEnabled.value = it
                }
            }
        }
    }

    fun onEvent(event: SettingsEvents){
        when(event){
            is SettingsEvents.ChangePasswordState -> {
               _passwordEnabled.value = event.state
            }
            is SettingsEvents.ChangeChameleonState -> {
                _chameleonEnabled.value = event.state
            }
        }
    }
}