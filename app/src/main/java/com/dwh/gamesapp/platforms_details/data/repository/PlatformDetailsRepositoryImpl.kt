package com.dwh.gamesapp.platforms_details.data.repository

import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms_details.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms_details.domain.repository.PlatformDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlatformDetailsRepositoryImpl@Inject constructor(
    private val apiService: ApiService
): PlatformDetailsRepository, BaseRepo() {
    override suspend fun getPlatformDetailsFromApi(id: Int): Flow<DataState<PlatformDetails>> =
        safeApiCall { apiService.getPlatformDetails(id) }.map { platformDetailsDTODataState ->
            platformDetailsDTODataState.mapper { platformDetailsDTO -> platformDetailsDTO.mapToDomain() }
        }
}