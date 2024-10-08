package com.dwh.gamesapp.platforms_details.navigation

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
import com.dwh.gamesapp.core.presentation.utils.sharedViewModel
import com.dwh.gamesapp.platforms.presentation.PlatformViewModel
import com.dwh.gamesapp.platforms_details.presentation.PlatformDetailsScreen
import com.dwh.gamesapp.platforms_details.presentation.PlatformDetailsViewModel

fun NavGraphBuilder.platformDetailsGraph(navController: NavController) {
    composable(
        route = NavigationScreens.PlatformDetails.route,
        arguments = listOf(navArgument("platformId") { type = NavType.IntType }),
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
        val platformId = backStackEntry.arguments?.getInt("platformId", 0)

        val viewModel = hiltViewModel<PlatformDetailsViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val platformViewModel = backStackEntry.sharedViewModel<PlatformViewModel>(navController)
        val platformState by platformViewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(viewModel) {
            if (platformId != null) {
                viewModel.getPlatformDetails(platformId)
            }
        }

        PlatformDetailsScreen(
            viewModel = viewModel,
            state = state,
            platformGames = platformState.platformGames,
            platformId = platformId,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}