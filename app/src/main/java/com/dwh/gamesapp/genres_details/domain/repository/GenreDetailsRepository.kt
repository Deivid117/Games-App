package com.dwh.gamesapp.genres_details.domain.repository

import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.core.presentation.state.DataState
import kotlinx.coroutines.flow.Flow

interface GenreDetailsRepository {
    suspend fun getGenreDetailsFromApi(id: Int): Flow<DataState<GenreDetails>>
}