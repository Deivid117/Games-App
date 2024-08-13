package com.dwh.gamesapp.games_details.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.games_details.presentation.GameDetailsScreen

fun NavGraphBuilder.gameDetailsGraph(navController: NavController) {
    composable(
        route = NavigationScreens.GameDetails.route,
        arguments = listOf(navArgument("gameId") { type = NavType.StringType }),
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
        val gameId = backStackEntry.arguments?.getString("gameId", "0")

        GameDetailsScreen(navController, gameId)
    }
}