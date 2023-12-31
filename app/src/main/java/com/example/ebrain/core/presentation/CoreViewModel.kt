package com.example.ebrain.core.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.ViewModel
import com.example.ebrain.R
import com.example.ebrain.core.domain.util.DataStore
import com.example.ebrain.core.domain.util.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoreViewModel @Inject constructor(
    private val dataStore: DataStore,
    private val languageManager: Language
) : ViewModel() {
    private val _state = mutableStateOf(CoreState())
    val state: State<CoreState> = _state

    fun enableVisitorMode() {
        _state.value = state.value.copy(
            visitorMode = true
        )
    }

    fun disableVisitorMode() {
        _state.value = state.value.copy(
            visitorMode = false
        )
    }

    fun onThemeCheckedChange() {
        _state.value = state.value.copy(
            themeChecked = !state.value.themeChecked
        )
    }

    fun onLanguageImageToggled() {
        if (_state.value.flag == R.drawable.usa) {

            _state.value = _state.value.copy(
                flag = R.drawable.sudan,
                layoutDirection = LayoutDirection.Rtl,
                code = "ar"
            )

            // to save flag uncomment this
            /**scope.launch {
            dataStore.saveAppLanguageFlag(flag)
            }**/

        } else {
            _state.value = _state.value.copy(
                flag = R.drawable.usa,
                layoutDirection = LayoutDirection.Ltr,
                code = "en"
            )

            // to save language uncomment this
            /**scope.launch {
            dataStore.saveAppLanguageFlag(flag)
            }**/
        }
    }

    fun setLayoutDirectionAndLanguage() {
        /**Setting language**/
        if (_state.value.flag == R.drawable.usa) {

            _state.value = _state.value.copy(
                layoutDirection = LayoutDirection.Ltr,
                code = "en"
            )

        } else {
            _state.value = _state.value.copy(
                layoutDirection = LayoutDirection.Rtl,
                code = "ar"
            )
        }
    }
}