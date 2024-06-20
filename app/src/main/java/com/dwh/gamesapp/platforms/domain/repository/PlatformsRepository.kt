package com.dwh.gamesapp.platforms.domain.repository

import com.dwh.gamesapp.a.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.platforms.domain.model.PlatformResults
import com.dwh.gamesapp.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface PlatformsRepository {

    // GET PLATFORMS
    suspend fun getPlatformsFromApi(): Flow<Resource<PlatformResults>>

    //suspend fun getAllPlatforms(): Flow<List<PlatformResults>>

    // GET PLATFORM DETAILS
    suspend fun getPlatformDetailsFromApi(id: Int): Flow<PlatformDetails>

    suspend fun getPlatformDetails(id: Int): Flow<PlatformDetails>
}