@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.login.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.KeyboardAware
import com.dwh.gamesapp.login.presentation.components.LogInBackgroundContent
import com.dwh.gamesapp.login.presentation.components.LogInElevatedCardContent

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    state: LoginState,
    navigateToSignUp: () -> Unit
) {
    /*val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.contra_game_intro)

    LaunchedEffect(Unit) {
        delay(700)
        mediaPlayer.start()

    }*/

    GameScaffold(
        isTopAppBarVisible = false,
        isBottomBarVisible = false,
        showBackgroundGradient = false,
        showSnackBarDismissAction = true,
        isSnackBarVisible = state.isSnackBarVisible,
        snackBarMessage = stringResource(id = R.string.login_snackbar_message),
        onDismissSnackBar = { viewModel.hideSnackBar() }
    ) {
        LoginView(
            viewModel = viewModel,
            state = state,
            navigateToSignUp = navigateToSignUp
        )
    }
}

@Composable
fun LoginView(
    viewModel: LoginViewModel,
    state: LoginState,
    navigateToSignUp: () -> Unit
) {
    LogInBackgroundContent()

    KeyboardAware {
        LogInElevatedCardContent(
            email = state.email,
            password = state.password,
            emailError = state.emailError,
            passwordError = state.passwordError,
            onEmailChanged = { viewModel.onEmailChanged(it) },
            onPasswordChanged = { viewModel.onPasswordChange(it) },
            onClickLogin = { viewModel.loginUser(email = state.email, password = state.password) },
            anErrorOccurred = viewModel.validationEmptyFields() || state.formHasErrors,
            onClickRememberUser = { viewModel.saveUserSessionFromPreferences(it) },
            navigateToSignUp = navigateToSignUp
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun zoomInFadeInEnterTransition(): EnterTransition {
    return scaleIn(animationSpec = tween(1250))
}

@OptIn(ExperimentalAnimationApi::class)
fun zoomOutFadeOutExitTransition(): ExitTransition {
    return scaleOut(targetScale = 1.2f, animationSpec = tween(1000)) +
            fadeOut(animationSpec = tween(1000))
}
