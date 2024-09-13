package com.dwh.gamesapp.core.domain.preferences.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveUserId(userId: Long)

    fun getUserId(): Flow<Long>

    suspend fun saveUserSession(loggedIn: Boolean)

    fun isUserLoggedIn(): Flow<Boolean>

    suspend fun saveFavoriteTheme(theme: String)

    fun getFavoriteTheme(): Flow<String>
}