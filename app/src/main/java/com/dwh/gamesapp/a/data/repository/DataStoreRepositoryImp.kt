package com.dwh.gamesapp.a.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dwh.gamesapp.a.domain.model.user.UserDataStore
import com.dwh.gamesapp.a.domain.repository.DataStoreRepository
import com.dwh.gamesapp.core.presentation.utils.Constants
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
        var userImageId = intPreferencesKey(Constants.USER_IMAGE_ID)
        var userIsLogged = booleanPreferencesKey(Constants.USER_IS_LOGGED)
    }

    override suspend fun setUserData(user: UserDataStore) {
        pref.edit {preferences ->
            preferences[userId] = user.id
            preferences[userEmail] = user.email
            preferences[userName] = user.userName
            preferences[userPassword] = user.password
            preferences[userImageId] = user.imageId
            preferences[userIsLogged] = user.isLogged
        }
    }

    override suspend fun getUserData() = pref.data.map { preferences ->
        UserDataStore(
            id = preferences[userId] ?: 0,
            userName = preferences[userName].orEmpty(),
            email = preferences[userEmail].orEmpty(),
            password = preferences[userPassword].orEmpty(),
            imageId = preferences[userImageId] ?: 0,
            isLogged = preferences[userIsLogged] ?: false
        )
    }
}