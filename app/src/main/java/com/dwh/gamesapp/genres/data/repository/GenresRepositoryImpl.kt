package com.dwh.gamesapp.genres.data.repository

import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.genres.domain.model.Genre
import com.dwh.gamesapp.genres.domain.repository.GenresRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val gameApiService: GameApiService
): GenresRepository, BaseRepo() {
    override suspend fun getGenresFromApi(): Flow<DataState<List<Genre>>> =
        safeApiCall { gameApiService.getGenres() }.map { resultDTODataState ->
            resultDTODataState.mapper { genreDTO -> genreDTO.results.map { it.mapToDomain() } }
        }
}