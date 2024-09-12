@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.edit_profile.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.KeyboardAware
import com.dwh.gamesapp.edit_profile.presentation.components.EditProfileBackgroundContent
import com.dwh.gamesapp.edit_profile.presentation.components.EditProfileElevatedCardContent

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel,
    state: EditProfileState
) {
    GameScaffold(
        navController = navController,
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        isBottomBarVisible = false,
        isTopAppBarVisible = true,
        showTopAppBarColor = true,
        showBackgroundGradient = false,
        topAppBarTitle = stringResource(id = R.string.edit_profile_title),
        onBackClick = { navController.popBackStack() }
    ) {
        EditProfileView(viewModel = viewModel, state = state)
    }
}

@Composable
fun EditProfileView(
    viewModel: EditProfileViewModel,
    state: EditProfileState
) {
    EditProfileBackgroundContent()

    KeyboardAware {
        EditProfileElevatedCardContent(
            userProfileAvatar = state.userProfileAvatar,
            name = state.name,
            password = state.password,
            confirmPassword = state.confirmPassword,
            nameError = state.nameError,
            passwordError = state.passwordError,
            passwordConfirmationError = state.confirmPasswordError,
            formHasErrors = viewModel.validationPasswordFields() || state.formHasErrors,
            onNameChanged = { viewModel.onNameChange(it) },
            onPasswordChanged = { viewModel.onPasswordChange(it) },
            onPasswordConfirmationChanged = { viewModel.onConfirmPasswordChange(it) },
            onClickUserAvatar = { viewModel.handleAvatarsModalBottomSheet(true) },
            onClickSaveButton = {
                viewModel.updateUser(
                    id = state.userId,
                    name = state.name.trim(),
                    password = state.password,
                    profileAvatarId = state.profileAvatarId
                )
            }
        )
    }
}