package com.dwh.gamesapp.genres.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.sharedViewModel
import com.dwh.gamesapp.genres.presentation.GenreScreen
import com.dwh.gamesapp.genres.presentation.GenreViewModel

fun NavGraphBuilder.genreGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Genre.route,
        enterTransition = {
            when (initialState.destination.route) {
                // Home to Genres, la animación se aplica a Genres
                NavigationScreens.Home.route -> slideIntoContainer(
                    animationSpec = tween(600),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
                // Genres Details back to Genres, la animación se aplica a Genres
                else -> null
            }
        },
        // Genres to Genres Details, la animación se aplica a Genres
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(600)
            )
        },
        // Genres back to Home, la animación se aplica a Genres
        popExitTransition = {
            fadeOut(
                animationSpec = tween(600)
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            )
        }
    ) { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<GenreViewModel>(navController)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        GenreScreen(navController, viewModel, state)
    }
}