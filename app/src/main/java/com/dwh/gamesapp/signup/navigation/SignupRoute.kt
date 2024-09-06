package com.dwh.gamesapp.signup.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.signup.presentation.SignupScreen
import com.dwh.gamesapp.signup.presentation.SignupViewModel
import com.dwh.gamesapp.signup.presentation.components.AvatarModalBottomSheet
import com.dwh.gamesapp.signup.presentation.components.SuccessfulRegistrationDialog

fun NavGraphBuilder.registrationGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Signup.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Left ,
            )
        },
        popExitTransition = {
            /*fadeOut(
                animationSpec = tween(Constants.ANIMATION_DURATION)
            ) + */slideOutOfContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }
    ) {
        val viewModel = hiltViewModel<SignupViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        if (state.isVisibleAvatarsModalBottomSheet) {
            AvatarModalBottomSheet(
                onAccept = { avatarId, avatarImage ->
                    viewModel.handleAvatarsModalBottomSheet(false)
                    viewModel.setUserAvatar(id = avatarId, image = avatarImage)
                },
                onDismiss = { viewModel.handleAvatarsModalBottomSheet(false) }
            )
        }

        if (state.isVisibleSuccessDialog) {
            SuccessfulRegistrationDialog {
                viewModel.hideSuccessDialog()
                navController.navigate(Screens.HOME_SCREEN.name)
            }
        }

        SignupScreen(
            viewModel = viewModel,
            state = state,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}