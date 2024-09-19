package com.dwh.gamesapp.login.domain.repository

import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun loginUser(email: String, password: String): Flow<DataState<User?>>

    suspend fun loginUserWithFingerPrintToken(token: EncryptedData): Flow<DataState<User?>>

    suspend fun updateUserFingerPrintToken(token: EncryptedData, userId: Long): Flow<DataState<Unit>>
}