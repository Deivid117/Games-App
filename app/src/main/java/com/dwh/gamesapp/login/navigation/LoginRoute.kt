package com.dwh.gamesapp.login.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.login.presentation.LoginScreen
import com.dwh.gamesapp.login.presentation.LoginViewModel
import com.dwh.gamesapp.login.presentation.zoomInFadeInEnterTransition
import com.dwh.gamesapp.login.presentation.zoomOutFadeOutExitTransition

fun NavGraphBuilder.loginGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Login.route,
        enterTransition = {
            scaleIn( animationSpec = tween(1250))
            //zoomInFadeInEnterTransition()

            /*slideInHorizontally(initialOffsetX = { it },
            animationSpec = tween(
                durationMillis = 3500,
                easing = LinearEasing
            ))*/
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(Constants.ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Right ,
            )
        }, popExitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
            //shrinkOut(animationSpec = tween(4000))
        }
        /*popExitTransition = {
            fadeOut(
                animationSpec = tween(Constants.ANIMATION_DURATION)
            ) + slideOutOfContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Up
            )
        }*/
    ) {
        val viewModel = hiltViewModel<LoginViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(state.wasUserFound) {
            if (state.wasUserFound) navController.navigate(Screens.HOME_SCREEN.name)
        }

        LoginScreen(
            viewModel = viewModel,
            state = state,
            navigateToSignUp = { navController.navigate(Screens.SIGNUP_SCREEN.name) }
        )
    }
}