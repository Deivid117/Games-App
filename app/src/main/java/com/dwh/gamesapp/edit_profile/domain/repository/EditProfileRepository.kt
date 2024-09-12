package com.dwh.gamesapp.edit_profile.domain.repository

import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface EditProfileRepository {
    suspend fun updateUser(name: String, password: String, profileAvatarId: Long, id: Long) : Flow<DataState<Unit>>
}