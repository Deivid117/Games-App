package com.dwh.gamesapp.profile.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.domain.enums.ThemeValues
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.core.presentation.utils.DoubleBackPressHandler
import com.dwh.gamesapp.profile.presentation.ProfileScreen
import com.dwh.gamesapp.profile.presentation.ProfileViewModel
import com.dwh.gamesapp.profile.presentation.components.LogoutDialog
import com.dwh.gamesapp.profile.presentation.components.ThemeModalBottomSheet

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Profile.route,
        exitTransition = { fadeOut(animationSpec = tween(Constants.ANIMATION_DURATION)) }
    ) {
        val viewModel = hiltViewModel<ProfileViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val themes = listOf(
            ThemeValues.LIGHT_MODE.title,
            ThemeValues.DARK_MODE.title,
            ThemeValues.SYSTEM_DEFAULT.title
        )
        var themeSelected by remember { mutableIntStateOf(2) }

        DoubleBackPressHandler()

        LaunchedEffect(state.userId) {
            viewModel.getUserId()
            viewModel.getUserInfo(state.userId)
        }

        LaunchedEffect(state.favoriteTheme) {
            viewModel.getFavoriteTheme()
            themeSelected = themes.indexOf(state.favoriteTheme)
        }

        if (state.isVisibleLogoutDialog) {
            LogoutDialog(
                onAccept = {
                    viewModel.logoutUser()
                    navController.navigate(Screens.LOGIN_SCREEN.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onDismiss = { viewModel.handleLogoutDialog(false) })
        }

        if (state.isVisibleThemeModalBottomSheet) {
            ThemeModalBottomSheet(
                themeOptions = themes,
                defaultSelected = themeSelected,
                onAccept = { viewModel.setFavoriteTheme(themes[it]) },
                onDismiss = { viewModel.handleThemeModalBottomSheet(false) }
            )
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