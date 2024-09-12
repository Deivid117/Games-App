package com.dwh.gamesapp.edit_profile.navigation

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
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameAvatarModalBottomSheet
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.edit_profile.presentation.EditProfileScreen
import com.dwh.gamesapp.edit_profile.presentation.EditProfileViewModel
import com.dwh.gamesapp.edit_profile.presentation.components.UpdateSuccessDialog

fun NavGraphBuilder.editProfileGraph(navController: NavController) {
    composable(
        route = NavigationScreens.EditProfile.route,
        arguments = listOf(
            navArgument("profileAvatar") { type = NavType.IntType },
            navArgument("profileAvatarId") { type = NavType.LongType },
            navArgument("userName") { type = NavType.StringType }
        ),
        enterTransition = {
            when (initialState.destination.route) {
                Screens.PROFILE_SCREEN.name -> slideIntoContainer(
                    animationSpec = tween(Constants.ANIMATION_DURATION),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
                else -> null
            }
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(Constants.ANIMATION_DURATION)
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(Constants.ANIMATION_DURATION)
            )
        }
    ) { backStackEntry ->
        val profileAvatarId = backStackEntry.arguments?.getLong("profileAvatarId", 0)
        val profileAvatar = backStackEntry.arguments?.getInt("profileAvatar", R.drawable.ic_user_unfilled)
        val userName = backStackEntry.arguments?.getString("userName", "")

        val viewModel = hiltViewModel<EditProfileViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.getUserId()
            if (profileAvatar != null && profileAvatarId != null) viewModel.setUserAvatar(profileAvatarId, profileAvatar)
            if (userName != null) viewModel.setUserName(userName)
        }

        if (state.isVisibleAvatarsModalBottomSheet) {
            GameAvatarModalBottomSheet(
                onAccept = { avatarId, avatarImage ->
                    viewModel.handleAvatarsModalBottomSheet(false)
                    viewModel.setUserAvatar(id = avatarId, image = avatarImage)
                },
                onDismiss = { viewModel.handleAvatarsModalBottomSheet(false) }
            )
        }

        if (state.isVisibleSuccessDialog) {
            UpdateSuccessDialog {
                viewModel.hideSuccessDialog()
                navController.navigateUp()
            }
        }

        EditProfileScreen(
            navController = navController,
            viewModel = viewModel,
            state = state
        )
    }
}