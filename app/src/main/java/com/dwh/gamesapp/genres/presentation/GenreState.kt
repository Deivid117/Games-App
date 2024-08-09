package com.dwh.gamesapp.genres.presentation

import com.dwh.gamesapp.genres.domain.model.Genre

data class GenreState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = listOf(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)