package com.dwh.gamesapp.profile.domain.repository

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserInformation(id: Long): Flow<DataState<User?>>
}