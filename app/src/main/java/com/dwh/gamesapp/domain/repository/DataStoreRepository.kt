package com.dwh.gamesapp.domain.repository

import com.dwh.gamesapp.domain.model.user.UserDataStore
import com.dwh.gamesapp.presentation.ui.demo.data.store.Data
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUserData(user: UserDataStore)

    suspend fun getUserData(): Flow<UserDataStore>
}