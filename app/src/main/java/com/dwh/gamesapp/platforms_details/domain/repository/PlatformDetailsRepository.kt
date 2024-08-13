package com.dwh.gamesapp.platforms_details.domain.repository

import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface PlatformDetailsRepository {
    suspend fun getPlatformDetailsFromApi(id: Int): Flow<DataState<PlatformDetails>>
}