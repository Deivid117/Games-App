package com.dwh.gamesapp.domain.repository

import com.dwh.gamesapp.domain.model.genre_details.GenreDetails
import com.dwh.gamesapp.domain.model.genres.GenresResults
import kotlinx.coroutines.flow.Flow

interface GenresRepository {

    // GET GENRES
    suspend fun getGenresFromApi(): Flow<List<GenresResults>>

    suspend fun getAllGenres(): Flow<List<GenresResults>>

    // GET GENRE DETAILS
    suspend fun getGenreDetailsFromApi(id: Int): Flow<GenreDetails>

    suspend fun getGenreDetails(id: Int): Flow<GenreDetails>
}