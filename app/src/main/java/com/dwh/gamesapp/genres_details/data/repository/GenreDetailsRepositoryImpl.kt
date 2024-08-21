package com.dwh.gamesapp.genres_details.data.repository

import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres_details.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.genres_details.domain.repository.GenreDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreDetailsRepositoryImpl @Inject constructor(
    private val gameApiService: GameApiService
): GenreDetailsRepository, BaseRepo() {
    override suspend fun getGenreDetailsFromApi(id: Int): Flow<DataState<GenreDetails>> =
        safeApiCall { gameApiService.getGenreDetails(id) }.map { genreDetailsDTODataState ->
            genreDetailsDTODataState.mapper { genreDetailsDTO -> genreDetailsDTO.mapToDomain() }
        }
}