package com.dwh.gamesapp.genres_details.presentation

import com.dwh.gamesapp.genres_details.domain.model.GenreDetails

data class GenreDetailsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isRefreshing: Boolean = false,
    val genreDetails: GenreDetails? = GenreDetails(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)