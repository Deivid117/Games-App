package com.dwh.gamesapp.platforms.data.repository

import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.BaseResponse
import com.dwh.gamesapp.platforms.domain.model.PlatformResults
import com.dwh.gamesapp.platforms.domain.model.toDomain
import com.dwh.gamesapp.platforms.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PlatformsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): PlatformsRepository, BaseResponse() {
    override suspend fun getPlatformsFromApi(): Flow<Resource<PlatformResults>> =
        safeApiCall {
            apiService.getPlatforms()
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
}