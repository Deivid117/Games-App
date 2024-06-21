package com.dwh.gamesapp.a.presentation.ui.demo.store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dwh.gamesapp.core.presentation.utils.Constants.DATA_STORE_NAME
import kotlinx.coroutines.flow.map

class DataStorePreferences(context: Context) {

    private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    val pref = context.dataStore

    companion object {
        var value1 = stringPreferencesKey("VALUE1")
        var value2 = intPreferencesKey("VALUE2")
    }

    suspend fun setValues(values: Data) {
        pref.edit { preferences ->
            preferences[value1] = values.value1
            preferences[value2] = values.value2
        }
    }

    fun getValues() = pref.data.map { preferences ->
        Data(
            value1 = preferences[value1].orEmpty(),
            value2 = preferences[value2] ?: 0
        )
    }
}