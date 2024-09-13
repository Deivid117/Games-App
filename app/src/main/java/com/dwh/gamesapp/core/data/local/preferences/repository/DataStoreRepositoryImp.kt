package com.dwh.gamesapp.core.data.local.preferences.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.dwh.gamesapp.core.data.local.preferences.PreferenceKeys
import com.dwh.gamesapp.core.domain.enums.ThemeValues
import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class DataStoreRepositoryImp(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override suspend fun saveUserId(userId: Long) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.USER_ID] = userId
        }
    }

    override fun getUserId() = dataStore.data.catch {
        emit(emptyPreferences())
    }.map { value -> value[PreferenceKeys.USER_ID] ?: 0L }

    override suspend fun saveUserSession(loggedIn: Boolean) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.IS_USER_LOGGED_IN] = loggedIn
        }
    }

    override fun isUserLoggedIn() = dataStore.data.catch {
        emit(emptyPreferences())
    }.map { value -> value[PreferenceKeys.IS_USER_LOGGED_IN] ?: false }

    override suspend fun saveFavoriteTheme(theme: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.FAVORITE_THEME] = theme
        }
    }

    override fun getFavoriteTheme() = dataStore.data.catch {
        emit(emptyPreferences())
    }.map { value -> value[PreferenceKeys.FAVORITE_THEME] ?: ThemeValues.SYSTEM_DEFAULT.title }
}