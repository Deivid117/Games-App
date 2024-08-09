package com.dwh.gamesapp.genres_details.data.repository

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.ApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.genres_details.domain.model.toDomain
import com.dwh.gamesapp.genres_details.domain.repository.GenreDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreDetailsRepositoryImpl@Inject constructor(
    private val apiService: ApiService
): GenreDetailsRepository, BaseRepo() {
    override suspend fun getGenreDetailsFromApi(id: Int): Flow<Resource<GenreDetails>> =
        safeApiCall2 {
            apiService.getGenreDetails(id)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
}