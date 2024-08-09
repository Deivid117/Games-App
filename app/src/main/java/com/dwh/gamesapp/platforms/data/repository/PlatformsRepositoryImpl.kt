package com.dwh.gamesapp.platforms.data.repository

import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.platforms.domain.model.Platform
import com.dwh.gamesapp.platforms.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlatformsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : PlatformsRepository, BaseRepo() {
    override suspend fun getPlatformsFromApi(): Flow<DataState<List<Platform>>> =
        safeApiCall { apiService.getPlatforms() }.map { resultDTODataState ->
            resultDTODataState.mapper { platformsDTO -> platformsDTO.results.map { it.mapToDomain() } }
        }
}