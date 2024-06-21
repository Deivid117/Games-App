package com.dwh.gamesapp.platforms.domain.repository

import com.dwh.gamesapp.platforms.domain.model.PlatformResults
import com.dwh.gamesapp.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface PlatformsRepository {
    suspend fun getPlatformsFromApi(): Flow<Resource<PlatformResults>>
}