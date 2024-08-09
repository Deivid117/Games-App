package com.dwh.gamesapp.platforms.domain.repository

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms.domain.model.Platform
import kotlinx.coroutines.flow.Flow

interface PlatformsRepository {
    suspend fun getPlatformsFromApi(): Flow<DataState<List<Platform>>>
}