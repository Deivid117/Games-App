package com.dwh.gamesapp.login.navigation

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
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
import com.dwh.gamesapp.core.presentation.composables.GameEnableBiometricDialog
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.core.presentation.utils.Constants.ENCRYPTED_FILE_NAME
import com.dwh.gamesapp.core.presentation.utils.Constants.PREFERENCES_DATA_STORE
import com.dwh.gamesapp.login.presentation.LoginScreen
import com.dwh.gamesapp.login.presentation.LoginViewModel
import com.dwh.gamesapp.utils.BiometricHelper

fun NavGraphBuilder.loginGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Login.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(Constants.ANIMATION_DURATION),
                towards = AnimatedContentTransitionScope.SlideDirection.Down,
            )
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
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
            )
        },
        popExitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Down)
        }
    ) {
        val viewModel = hiltViewModel<LoginViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current as FragmentActivity
        val isBiometricAvailable = remember { BiometricHelper.isBiometricAvailable(context) }

        LaunchedEffect(isBiometricAvailable) {
            if (isBiometricAvailable) {
                viewModel.getFingerPrintEncrypted(
                    context = context,
                    filename = ENCRYPTED_FILE_NAME,
                    prefKey = PREFERENCES_DATA_STORE,
                    mode = Context.MODE_PRIVATE
                )
                viewModel.checkIfBiometricLoginEnabled()
            }
        }

        /*LaunchedEffect(state.isBiometricPromptVisible) {
            if (state.isBiometricPromptVisible) {
                *//*BiometricHelper.authenticateUser(
                    context = context,
                    onSuccess = { _, encryptedToken ->
                        viewModel.loginUserWithFingerPrintToken(encryptedToken)
                    }
                )*//*
            }
        }*/

        if (state.isBiometricDialogVisible) {
            GameEnableBiometricDialog(
                onEnable = {
                    BiometricHelper.registerUserBiometrics(context) { _, encryptedToken ->
                        viewModel.hideBiometricDialog()
                        viewModel.setBiometricEnabled(true)
                        viewModel.updateUser(token = encryptedToken, userId = state.userIdFounded)
                    }
                },
                onDismiss = {
                    viewModel.hideBiometricDialog()
                    navController.navigate(Screens.HOME_SCREEN.name)
                }
            )
        }

        LaunchedEffect(state.wasUserFound) {
            if (state.wasUserFound) navController.navigate(Screens.HOME_SCREEN.name)
        }

        LoginScreen(
            viewModel = viewModel,
            state = state,
            isBiometricAvailable = isBiometricAvailable,
            navigateToSignUp = { navController.navigate(Screens.SIGNUP_SCREEN.name) }
        )
    }
}