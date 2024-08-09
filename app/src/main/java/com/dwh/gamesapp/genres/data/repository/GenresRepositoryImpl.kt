package com.dwh.gamesapp.genres.data.repository

import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.genres.domain.model.GenresResults
import com.dwh.gamesapp.genres.domain.model.toDomain
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): GenresRepository, BaseRepo() {
    override suspend fun getGenresFromApi(): Flow<Resource<GenresResults>> =
        safeApiCall2 {
            apiService.getGenres()
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
}