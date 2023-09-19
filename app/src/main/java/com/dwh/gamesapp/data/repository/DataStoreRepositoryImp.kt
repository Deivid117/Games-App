package com.dwh.gamesapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dwh.gamesapp.domain.model.user.UserDataStore
import com.dwh.gamesapp.domain.repository.DataStoreRepository
import com.dwh.gamesapp.presentation.ui.demo.data.store.Data
import com.dwh.gamesapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.DATA_STORE_NAME)

class DataStoreRepositoryImp(
    context: Context
) : DataStoreRepository {

    private val pref = context.dataStore

    companion object {
        var userName = stringPreferencesKey(Constants.USER_NAME)
        var userEmail = stringPreferencesKey(Constants.USER_EMAIL)
        var userId = intPreferencesKey(Constants.USER_ID)
        var userPassword = stringPreferencesKey(Constants.USER_PASSWORD)
        var userIsLogged = booleanPreferencesKey(Constants.USER_IS_LOGGED)
    }

    override suspend fun setUserData(user: UserDataStore) {
        pref.edit {preferences ->
            preferences[userId] = user.id
            preferences[userEmail] = user.email
            preferences[userName] = user.userName
            preferences[userPassword] = user.password
            preferences[userIsLogged] = user.isLogged
        }
    }

    override suspend fun getUserData() = pref.data.map { preferences ->
        UserDataStore(
            id = preferences[userId] ?: 0,
            userName = preferences[userName].orEmpty(),
            email = preferences[userEmail].orEmpty(),
            password = preferences[userPassword].orEmpty(),
            isLogged = preferences[userIsLogged] ?: false
        )
    }
}