package com.example.ebrain.core.domain.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(
    private val context: Context
) {
    // to make sure there is only one instance
    companion object {
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("Pref")


        val PASSWORD_STATE_KEY = booleanPreferencesKey("password_state")
        val CHAMELEON_FEATURE_KEY = booleanPreferencesKey("chameleon_feature")
        val PASSWORD_VALUE_KEY = stringPreferencesKey("password_value")
        val CATEGORY_SCREEN = stringPreferencesKey("category_screen")
        val VISITOR_MODE = booleanPreferencesKey("visitor_mode")
    }


    /**
     * if you want to save theme state & language state

     theme

     *  val THEME_KEY = booleanPreferencesKey("theme_mode")

     *  // to save the theme mode
     *     suspend fun saveThemeMode(mode: Boolean) {
     *         context.datastore.edit { preferences ->
     *             preferences[THEME_KEY] = mode
     *         }
     *     }

     * // to get the theme mode
     *     val getThemeMode: Flow<Boolean> = context.datastore.data.map { preferences ->
     *         preferences[THEME_KEY] ?: false
     *     }

     language

     *  val APP_LANGUAGE_FLAG = intPreferencesKey("app_language_flag")

     * // to save app language
     *     suspend fun saveAppLanguageFlag(flag: Int) {
     *         context.datastore.edit { preferences ->
     *             preferences[APP_LANGUAGE_FLAG] = flag
     *         }
     *     }

     * // to get app language
     *     val getAppLanguageFlag: Flow<Int> = context.datastore.data.map { preferences ->
     *         preferences[APP_LANGUAGE_FLAG] ?: R.drawable.usa
     *     }
     * **/


    // to get the password state
    val getPasswordState: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[PASSWORD_STATE_KEY] ?: false
    }

    // to get the chameleon feature state
    val getChameleonState: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[CHAMELEON_FEATURE_KEY] ?: false
    }

    // to get the password value
    val getPasswordValue: Flow<String?> = context.datastore.data.map { preferences ->
        preferences[PASSWORD_VALUE_KEY]
    }

    // to get the category screen name (note or task)
    val getCategoryScreenName: Flow<String> = context.datastore.data.map { preferences ->
        preferences[CATEGORY_SCREEN] ?: Screen.NotesScreen.route
    }



    // to get visitor mode
    val getVisitorModeState: Flow<Boolean> = context.datastore.data.map { preferences ->
        preferences[VISITOR_MODE] ?: false
    }

    //------------------------------------------------------------------------------------------------//

    // to save password
    suspend fun savePasswordState(state: Boolean) {
        context.datastore.edit { preferences ->
            preferences[PASSWORD_STATE_KEY] = state
        }
    }

    // to save chameleon state
    suspend fun saveChameleonState(state: Boolean) {
        context.datastore.edit { preferences ->
            preferences[CHAMELEON_FEATURE_KEY] = state
        }
    }

    // to save password value
    suspend fun savePasswordValue(password: String) {
        context.datastore.edit { preferences ->
            preferences[PASSWORD_VALUE_KEY] = password
        }
    }

    // to save category screen name
    suspend fun saveCategoryScreenName(screen: String) {
        context.datastore.edit { preferences ->
            preferences[CATEGORY_SCREEN] = screen
        }
    }


    // to save visitor mode
    suspend fun saveVisitorModeState(visitorModeState: Boolean) {
        context.datastore.edit { preferences ->
            preferences[VISITOR_MODE] = visitorModeState
        }
    }
}