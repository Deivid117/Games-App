package com.dwh.gamesapp.domain.repository

import com.dwh.gamesapp.domain.model.genre_details.GenreDetails
import com.dwh.gamesapp.domain.model.genres.GenresResults
import com.dwh.gamesapp.domain.model.plattform.PlattformResults
import com.dwh.gamesapp.domain.model.platform_details.PlatformDetails
import kotlinx.coroutines.flow.Flow

interface PlatformsRepository {

    // GET PLATFORMS
    suspend fun getPlatformsFromApi(): Flow<List<PlattformResults>>

    suspend fun getAllPlatforms(): Flow<List<PlattformResults>>

    // GET PLATFORM DETAILS
    suspend fun getPlatformDetailsFromApi(id: Int): Flow<PlatformDetails>

    suspend fun getPlatformDetails(id: Int): Flow<PlatformDetails>
}