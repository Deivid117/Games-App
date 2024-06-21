package com.dwh.gamesapp.genres.domain.repository

import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.genres.domain.model.GenresResults
import kotlinx.coroutines.flow.Flow

interface GenresRepository {

    // SÍ SE USA
    suspend fun getGenresFromApi(): Flow<Resource<GenresResults>>

    //suspend fun getAllGenres(): Flow<List<GenresResults>>

    // GET GENRE DETAILS
    suspend fun getGenreDetailsFromApi(id: Int): Flow<GenreDetails>

    suspend fun getGenreDetails(id: Int): Flow<GenreDetails>
}