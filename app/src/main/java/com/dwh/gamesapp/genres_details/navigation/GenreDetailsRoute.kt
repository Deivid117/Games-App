package com.dwh.gamesapp.genres_details.navigation

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
import com.dwh.gamesapp.core.presentation.navigation.sharedViewModel
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.genres.presentation.GenreViewModel
import com.dwh.gamesapp.genres_details.presentation.GenreDetailsScreen
import com.dwh.gamesapp.genres_details.presentation.GenreDetailsViewModel

fun NavGraphBuilder.genreDetailsGraph(navController: NavController) {
    composable(
        route = NavigationScreens.GenreDetails.route,
        arguments = listOf(navArgument("genreId") { type = NavType.IntType }),
        // Genres to Genres Details, la animación se aplica a Genres Details
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
            )
        },
        // Genres Details back to Genres, la animación se aplica a Genres Details
        popExitTransition = {
            fadeOut(
                animationSpec = tween(Constants.ANIMATION_DURATION)
            ) + slideOutOfContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }
    ) { backStackEntry ->
        val genreId = backStackEntry.arguments?.getInt("genreId", 0)

        val viewModel = hiltViewModel<GenreDetailsViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val genreViewModel = backStackEntry.sharedViewModel<GenreViewModel>(navController)
        val genreState by genreViewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            if (genreId != null) {
                viewModel.getGenreDetails(genreId)
            }
        }

        GenreDetailsScreen(
            viewModel = viewModel,
            state = state,
            genreGames = genreState.genreGames,
            genreId = genreId,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}