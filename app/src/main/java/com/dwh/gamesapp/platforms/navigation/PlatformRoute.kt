package com.dwh.gamesapp.platforms.navigation

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
import com.dwh.gamesapp.platforms.presentation.PlatformScreen
import com.dwh.gamesapp.platforms.presentation.PlatformViewModel

fun NavGraphBuilder.platformGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Platform.route,
        enterTransition = {
            when (initialState.destination.route) {
                NavigationScreens.Home.route -> slideIntoContainer(
                    animationSpec = tween(600),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
                else -> null
            }
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                animationSpec = tween(600)
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(600)
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            )
        }
    ) { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<PlatformViewModel>(navController)
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        PlatformScreen(navController, viewModel, state)
    }
}