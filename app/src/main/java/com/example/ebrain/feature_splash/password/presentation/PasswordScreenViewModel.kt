package com.example.ebrain.feature_splash.password.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PasswordScreenViewModel: ViewModel() {
    var state by mutableStateOf(PasswordScreenState())
        private set

    fun getPassword(password: String?){
        if (password != null){
            state = state.copy(
                password = password
            )
        }
    }
    fun insertNumber(number: Int){
        if (state.counter < 6){
            state = state.copy(
                counter = state.counter + 1,
                insertedNumber = state.insertedNumber + "$number"
            )
        }
    }

    fun deleteNumber(){
        if (state.counter > 0){
            state = state.copy(
                counter = state.counter - 1,
                insertedNumber = state.insertedNumber.dropLast(1)
            )
        }
    }

    fun isPassed(): Boolean{
        if (state.counter == 6){
            return state.insertedNumber == state.password
        }
        return false
    }

    fun reset(){
        viewModelScope.launch {
            for (i in 6 downTo 1){
                state = state.copy(
                    counter = state.counter - 1,
                    insertedNumber = state.insertedNumber.dropLast(1)
                )
                delay(300L)
            }
            state = PasswordScreenState()
        }
    }
}