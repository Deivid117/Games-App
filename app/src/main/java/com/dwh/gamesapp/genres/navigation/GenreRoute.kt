package com.dwh.gamesapp.genres.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.navigation.sharedViewModel
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.genres.presentation.GenreScreen
import com.dwh.gamesapp.genres.presentation.GenreViewModel

fun NavGraphBuilder.genreGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Genre.route,
        enterTransition = {
            when (initialState.destination.route) {
                // Home to Genres, la animación se aplica a Genres
                NavigationScreens.Home.route -> slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION),
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
                animationSpec = tween(Constants.ANIMATION_DURATION)
            )
        },
        // Genres back to Home, la animación se aplica a Genres
        popExitTransition = {
            fadeOut(
                animationSpec = tween(Constants.ANIMATION_DURATION)
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(Constants.ANIMATION_DURATION)
            )
        }
    ) { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<GenreViewModel>(navController)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.getGenres()
        }

        GenreScreen(
            navController = navController,
            viewModel = viewModel,
            state = state,
            navigateToGenreDetails = { navController.navigate("${Screens.GENRE_DETAILS_SCREEN.name}/$it") }
        )
    }
}