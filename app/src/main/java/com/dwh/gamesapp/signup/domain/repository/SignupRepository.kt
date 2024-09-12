package com.dwh.gamesapp.signup.domain.repository

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    suspend fun registerUser(user: User): Flow<DataState<Long>>

    suspend fun userAlreadyExists(email: String, userName: String): Flow<DataState<User?>>
}