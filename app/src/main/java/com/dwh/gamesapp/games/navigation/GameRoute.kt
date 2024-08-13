package com.dwh.gamesapp.games.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.games.presentation.GamesScreen
import com.dwh.gamesapp.games.presentation.GamesViewModel

fun NavGraphBuilder.gameGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Game.route,
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(Constants.ANIMATION_DURATION)
            )
        }
    ) {
        val viewModel = hiltViewModel<GamesViewModel>()

        GamesScreen(navController, viewModel)
    }
}