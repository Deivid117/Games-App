package com.dwh.gamesapp.core.data.local.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dwh.gamesapp.core.presentation.utils.Constants

object PreferenceKeys {
    var USER_ID = longPreferencesKey("user_id")
    var FAVORITE_THEME = stringPreferencesKey("favorite_theme")
    var KEY_TOKEN = stringPreferencesKey("user_token")
    var IS_USER_LOGGED_IN = booleanPreferencesKey("is_user_logged_in")
    var IS_BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
}