package com.dwh.gamesapp.genres.presentation

import com.dwh.gamesapp.genres.domain.model.Genre
import com.dwh.gamesapp.genres.domain.model.GenreGame

data class GenreState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = listOf(),
    val genreGames: List<GenreGame> = listOf(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)