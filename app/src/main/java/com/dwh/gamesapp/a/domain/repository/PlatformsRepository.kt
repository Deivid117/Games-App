package com.dwh.gamesapp.a.domain.repository

import com.dwh.gamesapp.a.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.a.domain.model.plattform.PlattformResults
import kotlinx.coroutines.flow.Flow

interface PlatformsRepository {

    // GET PLATFORMS
    suspend fun getPlatformsFromApi(): Flow<List<PlattformResults>>

    suspend fun getAllPlatforms(): Flow<List<PlattformResults>>

    // GET PLATFORM DETAILS
    suspend fun getPlatformDetailsFromApi(id: Int): Flow<PlatformDetails>

    suspend fun getPlatformDetails(id: Int): Flow<PlatformDetails>
}