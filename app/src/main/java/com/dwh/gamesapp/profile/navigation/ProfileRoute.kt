package com.dwh.gamesapp.profile.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
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
import com.dwh.gamesapp.profile.presentation.ProfileScreen
import com.dwh.gamesapp.profile.presentation.ProfileViewModel
import com.dwh.gamesapp.profile.presentation.components.LogoutDialog

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Profile.route,
        exitTransition = { fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION)) }
    ) {
        val viewModel = hiltViewModel<ProfileViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(state.userId) {
            viewModel.getUserId()
            viewModel.getUserInfo(state.userId)
        }

        if (state.isVisibleLogoutDialog) {
            LogoutDialog(
                onAccept = {
                    viewModel.logoutUser()
                    navController.navigate(Screens.LOGIN_SCREEN.name)
                },
                onDismiss = { viewModel.handleLogoutDialog(false) })
        }

        ProfileScreen(
            navController = navController,
            viewModel = viewModel,
            state = state,
            navigateToEditProfile = {
                navController.navigate("${Screens.EDIT_PROFILE_SCREEN.name}/${state.user.profileAvatarId}/${state.userProfileAvatar}/${state.user.name}")
            }
        )
    }
}