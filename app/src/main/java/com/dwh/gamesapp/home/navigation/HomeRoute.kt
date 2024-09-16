package com.dwh.gamesapp.home.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.home.presentation.HomeScreen
import com.dwh.gamesapp.home.presentation.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = NavigationScreens.Home.route, route = "home") {
        composable(
            route = NavigationScreens.Home.route,
            exitTransition = { fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION)) }
        ) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.getNextWeekGames()
                viewModel.getBestOfTheYear()
            }

            HomeScreen(
                navController = navController,
                viewModel = viewModel,
                state = state,
                navigateToGenres = { navController.navigate(Screens.GENRE_SCREEN.name) },
                navigateToPlatforms = { navController.navigate(Screens.PLATFORM_SCREEN.name) },
                navigateToGameDetails = { navController.navigate("${Screens.GAME_DETAILS_SCREEN.name}/$it") }
            )
        }
    }
}