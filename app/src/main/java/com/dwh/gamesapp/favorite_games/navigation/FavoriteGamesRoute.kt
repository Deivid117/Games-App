package com.dwh.gamesapp.favorite_games.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.core.presentation.utils.DoubleBackPressHandler
import com.dwh.gamesapp.favorite_games.presentation.FavoriteGamesScreen
import com.dwh.gamesapp.favorite_games.presentation.FavoriteGamesViewModel

fun NavGraphBuilder.favoriteGamesGraph(navController: NavController) {
    composable(
        route = NavigationScreens.FavoriteGames.route,
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(Constants.ANIMATION_DURATION)
            )
        }
    ) {
        val viewModel = hiltViewModel<FavoriteGamesViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        DoubleBackPressHandler()

        LaunchedEffect(key1 = viewModel) {
            viewModel.getAllFavoriteGames()
        }

        FavoriteGamesScreen(
            navController = navController,
            state = state,
            navigateToGameDetails ={ navController.navigate("${Screens.GAME_DETAILS_SCREEN.name}/$it") }
        )
    }
}