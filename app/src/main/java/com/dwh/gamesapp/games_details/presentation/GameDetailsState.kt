package com.dwh.gamesapp.games_details.presentation

import com.dwh.gamesapp.R
import com.dwh.gamesapp.games_details.domain.model.GameDetails

data class GameDetailsState(
    val isLoading: Boolean = false,
    val isLoadingInsert: Boolean = false,
    val isLoadingMyFavorite: Boolean = false,
    val isLoadingDelete: Boolean = false,
    val isError: Boolean = false,
    val isRefreshing: Boolean = false,
    val isMyFavoriteGame: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val snackBarMessage: String = "",
    val lottieAnimationSnackBar: Int = R.raw.heart_animation,
    val gameDetails: GameDetails? = GameDetails(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)