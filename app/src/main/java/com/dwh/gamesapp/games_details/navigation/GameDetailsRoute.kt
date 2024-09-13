package com.dwh.gamesapp.games_details.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.games_details.presentation.GameDetailsScreen
import com.dwh.gamesapp.games_details.presentation.GameDetailsViewModel

fun NavGraphBuilder.gameDetailsGraph(navController: NavController) {
    composable(
        route = NavigationScreens.GameDetails.route,
        arguments = listOf(navArgument("gameId") { type = NavType.StringType }),
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(Constants.ANIMATION_DURATION)
            ) + slideOutOfContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }
    ) { backStackEntry ->
        val gameId = backStackEntry.arguments?.getString("gameId", "0")
        val viewModel = hiltViewModel<GameDetailsViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            if (!gameId.isNullOrEmpty()) {
                viewModel.getGameDetails(gameId.toInt())
                //viewModel.isMyFavoriteGame(gameId.toInt())
            }
        }

        GameDetailsScreen(
            viewModel = viewModel,
            state = state,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}