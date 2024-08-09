package com.dwh.gamesapp.genres.domain.repository

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.genres.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    suspend fun getGenresFromApi(): Flow<DataState<List<Genre>>>
}