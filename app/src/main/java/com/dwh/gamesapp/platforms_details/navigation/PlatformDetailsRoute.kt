package com.dwh.gamesapp.platforms_details.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.sharedViewModel
import com.dwh.gamesapp.platforms.presentation.PlatformViewModel
import com.dwh.gamesapp.platforms_details.presentation.PlatformDetailsScreen

fun NavGraphBuilder.platformDetailsGraph(navController: NavController) {
    composable(
        route = NavigationScreens.PlatformDetails.route,
        arguments = listOf(navArgument("platformId") { type = NavType.IntType }),
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(600),
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(600)
            ) + slideOutOfContainer(
                animationSpec = tween(600),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }
    ) { backStackEntry ->
        val viewModel = backStackEntry.sharedViewModel<PlatformViewModel>(navController)
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val platformId = backStackEntry.arguments?.getInt("platformId", 0)

        PlatformDetailsScreen(navController, platformId, state)
    }
}