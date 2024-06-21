package com.dwh.gamesapp.genres.domain.repository

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.genres.domain.model.GenresResults
import kotlinx.coroutines.flow.Flow

interface GenresRepository {
    suspend fun getGenresFromApi(): Flow<Resource<GenresResults>>
}