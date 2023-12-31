package com.example.ebrain.feature_settings.edit_password.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebrain.core.domain.util.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPasswordViewModel@Inject constructor(
    private val dataStore: DataStore
):ViewModel() {

    var state by mutableStateOf(EditPasswordState())
        private set


    fun isAcceptedSetPassword(
        enteredPassword: String,
        reEnteredPassword: String
    ): Boolean {
        return enteredPassword == reEnteredPassword
                && enteredPassword.length == 6
                && !isNotAcceptedPassword(enteredPassword)
    }

    fun isAcceptedResetPassword(
        currentPassword: String,
        enteredPassword: String,
        reEnteredPassword: String
    ): Boolean {

        val password = getPassword()

        return currentPassword == password
                && enteredPassword == reEnteredPassword
                && enteredPassword.length == 6
                && !isNotAcceptedPassword(enteredPassword)
    }

    fun addPassword(enteredPassword: String) {
        state = state.copy(
            password = enteredPassword
        )

        viewModelScope.launch {
            dataStore.savePasswordValue(state.password!!)
        }
    }
    fun isNotAcceptedPassword(password: String): Boolean {
        var flag = 1

        password.forEach {
            flag *= if (it.isDigit()) {
                1
            } else {
                0
            }
        }

        return flag != 1
    }
    fun getPassword(): String{
        var password:String? = null

        viewModelScope.launch {
            dataStore.getPasswordValue.collectLatest {
                password = it
            }
        }
        return password ?: ""
    }
}