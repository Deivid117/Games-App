package com.dwh.gamesapp.signup.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.composables.GameEnableBiometricDialog
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.signup.presentation.SignupScreen
import com.dwh.gamesapp.signup.presentation.SignupViewModel
import com.dwh.gamesapp.core.presentation.composables.GameAvatarModalBottomSheet
import com.dwh.gamesapp.signup.presentation.components.SuccessfulRegistrationDialog
import com.dwh.gamesapp.utils.BiometricHelper

fun NavGraphBuilder.registrationGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Signup.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }
    ) {
        val viewModel = hiltViewModel<SignupViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current as FragmentActivity
        val isBiometricAvailable = remember { BiometricHelper.isBiometricAvailable(context) }

        LaunchedEffect(isBiometricAvailable) {
            if (isBiometricAvailable) {
                viewModel.getFingerPrintEncrypted(
                    context = context,
                    filename = Constants.ENCRYPTED_FILE_NAME,
                    prefKey = Constants.PREFERENCES_DATA_STORE,
                    mode = Context.MODE_PRIVATE
                )
            }
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

        if (state.isBiometricDialogVisible) {
            GameEnableBiometricDialog(
                onEnable = {
                    BiometricHelper.registerUserBiometrics(context) { _, encryptedToken ->
                        viewModel.handleBiometricDialog(false)
                        viewModel.setBiometricEnabled(true)
                        viewModel.signupUser(
                            user = User(
                                name = state.name,
                                email = state.email,
                                password = state.password,
                                profileAvatarId = state.profileAvatarId,
                                fingerPrintToken = encryptedToken
                            )
                        )
                    }
                },
                onDismiss = {
                    viewModel.handleBiometricDialog(false)
                    viewModel.signupUser(
                        user = User(
                            name = state.name,
                            email = state.email,
                            password = state.password,
                            profileAvatarId = state.profileAvatarId
                        )
                    )
                }
            )
        }

        if (state.isVisibleSuccessDialog) {
            SuccessfulRegistrationDialog {
                viewModel.handleSuccessDialog(false)
                navController.navigate(Screens.HOME_SCREEN.name)
            }
        }

        SignupScreen(
            viewModel = viewModel,
            state = state,
            isBiometricAvailable = isBiometricAvailable,
            onNavigateBack = { navController.navigateUp() }
        )
    }
}