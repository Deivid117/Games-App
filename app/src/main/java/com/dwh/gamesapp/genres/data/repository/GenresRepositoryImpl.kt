package com.dwh.gamesapp.genres.data.repository

import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.BaseResponse
import com.dwh.gamesapp.genres.domain.model.GenresResults
import com.dwh.gamesapp.genres.domain.model.toDomain
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): GenresRepository, BaseResponse() {
    override suspend fun getGenresFromApi(): Flow<Resource<GenresResults>> =
        safeApiCall {
            apiService.getGenres()
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
}