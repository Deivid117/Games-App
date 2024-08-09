package com.dwh.gamesapp.platforms_details.data.remote.repository

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms_details.domain.model.toDomain
import com.dwh.gamesapp.platforms_details.domain.repository.PlatformDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlatformDetailsRepositoryImpl@Inject constructor(
    private val apiService: ApiService
): PlatformDetailsRepository, BaseRepo() {
    override suspend fun getPlatformDetailsFromApi(id: Int): Flow<Resource<PlatformDetails>> =
        safeApiCall2 {
            apiService.getPlatformDetails(id)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
}