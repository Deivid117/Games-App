@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.dwh.gamesapp.favorite_games.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.favorite_games.presentation.components.VerticalStaggeredGridFavoriteGames

@Composable
fun FavoriteGamesScreen(
    navController: NavController,
    state: FavoriteGamesState,
    navigateToGameDetails: (Int) -> Unit
) {
    GameScaffold(navController = navController, isVisiblePullRefreshIndicator = false) {
        FavoriteGamesView(state = state, navigateToGameDetails = navigateToGameDetails)
    }
}

@Composable
fun FavoriteGamesView(state: FavoriteGamesState, navigateToGameDetails: (Int) -> Unit) {
    when {
        (!state.isLoading && state.favoriteGames.isEmpty()) -> {
            GameInformationalMessageCard(
                modifier = Modifier.fillMaxSize(),
                message = state.errorMessage,
                description = state.errorDescription
            )
        }
        else -> {
            VerticalStaggeredGridFavoriteGames(
                favoriteGames = state.favoriteGames,
                navigateToGameDetails = navigateToGameDetails
            )
        }
    }
}