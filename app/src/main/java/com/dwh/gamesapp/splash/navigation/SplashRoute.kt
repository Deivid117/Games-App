package com.dwh.gamesapp.splash.navigation

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.splash.presentation.SplashScreen
import com.dwh.gamesapp.splash.presentation.SplashViewModel

fun NavGraphBuilder.splashGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Splash.route,
        exitTransition = { slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down,
            animationSpec = tween(Constants.ANIMATION_DURATION)
        ) },
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        val viewModel = hiltViewModel<SplashViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.gba_startup)

        LaunchedEffect(viewModel) {
            mediaPlayer.start()
            viewModel.isUserLoggedIn()
        }

        SplashScreen(
            isUserLoggedIn = state.isUserLoggedIn,
            navigateTo = {
                navController.navigate(
                    if (state.isUserLoggedIn) Screens.GAME_SCREEN.name
                    else Screens.LOGIN_SCREEN.name
                ) {
                    popUpTo(route = Screens.SPLASH_SCREEN.name) {
                        inclusive = true
                    }
                }
            }
        )
    }
}