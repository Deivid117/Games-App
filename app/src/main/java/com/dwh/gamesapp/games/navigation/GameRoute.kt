package com.dwh.gamesapp.games.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.games.presentation.GameScreen
import com.dwh.gamesapp.games.presentation.GameViewModel

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
        val viewModel = hiltViewModel<GameViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val games = viewModel.pagingGames.collectAsLazyPagingItems()

        LaunchedEffect(Unit) {
            viewModel.getGames()
        }

        GameScreen(
            navController = navController,
            viewModel = viewModel,
            state = state,
            games = games,
            navigateToGameDetails = { navController.navigate("${Screens.GAME_DETAILS_SCREEN.name}/${it}") }
        )
    }
}