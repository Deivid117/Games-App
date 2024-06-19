package com.dwh.gamesapp.a.domain.repository

import com.dwh.gamesapp.a.domain.model.user.UserDataStore
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUserData(user: UserDataStore)

    suspend fun getUserData(): Flow<UserDataStore>
}