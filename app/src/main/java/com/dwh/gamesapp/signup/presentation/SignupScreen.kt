@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.signup.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.KeyboardAware
import com.dwh.gamesapp.core.presentation.composables.GameUserImage
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.composables.GameOutlinedButton
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.GameTitleGradientText
import com.dwh.gamesapp.core.presentation.theme.dark_green
import com.dwh.gamesapp.core.presentation.theme.light_green
import com.dwh.gamesapp.core.presentation.theme.light_green_background
import com.dwh.gamesapp.core.presentation.ui.UiText
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.login.presentation.components.RememberMeCheckBox
import com.dwh.gamesapp.signup.presentation.components.SignUpForm

@Composable
fun SignupScreen(
    viewModel: SignupViewModel,
    state: SignupState,
    isBiometricAvailable: Boolean,
    onNavigateBack: () -> Unit
) {
    GameScaffold(
        isTopAppBarVisible = false,
        isBottomBarVisible = false,
        showSnackBarDismissAction = true,
        showBackgroundGradient = false,
        isSnackBarVisible = state.isSnackBarVisible,
        snackBarMessage = state.snackBarMessage?.asString() ?: "",
        snackBarBorderColor = state.snackBarBorderColor,
        snackBarContainerColor = state.snackBarContainerColor,
        lottieAnimationSnackBar = state.lottieAnimationSnackBar,
        snackBarDuration = state.snackBarDuration,
        onDismissSnackBar = { viewModel.handleSnackBar(isVisible = false) }
    ) {
        GameBackgroundGradient(isVisiblePullRefreshIndicator = false) {
            SignupView(
                viewModel = viewModel,
                state = state,
                isBiometricAvailable = isBiometricAvailable,
                onNavigateBack = onNavigateBack
            )
        }
    }
}

@Composable
fun SignupView(
    viewModel: SignupViewModel,
    state: SignupState,
    isBiometricAvailable: Boolean,
    onNavigateBack: () -> Unit
) {
    val snackBarContainerColor = if (isDarkThemeEnabled()) dark_green else light_green_background
    val snackBarBorderColorError = MaterialTheme.colorScheme.error
    val snackBarContainerColorError = MaterialTheme.colorScheme.errorContainer

    KeyboardAware {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 15.dp)
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                shape = RoundedCornerShape(5.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp, vertical = 25.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GameTitleGradientText(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.signup_title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    GameUserImage(
                        onClick = { viewModel.handleAvatarsModalBottomSheet(true) },
                        image = state.profileAvatarImage
                    )

                    SignUpForm(
                        name = state.name,
                        email = state.email,
                        password = state.password,
                        passwordConfirmation = state.confirmPassword,
                        nameError = state.nameError,
                        emailError = state.emailError,
                        passwordError = state.passwordError,
                        passwordConfirmationError = state.confirmPasswordError,
                        onNameChanged = { viewModel.onNameChanged(it) },
                        onEmailChanged = { viewModel.onEmailChanged(it) },
                        onPasswordChanged = { viewModel.onPasswordChange(it) },
                        onPasswordConfirmationChanged = { viewModel.onConfirmPasswordChange(it) },
                        onClickLabelIcon = {
                            viewModel.handleSnackBar(
                                isVisible = true,
                                message = UiText.StringResource(R.string.signup_password_instruction),
                                lottieAnimation = R.raw.info_coin,
                                snackBarBorderColor = light_green,
                                snackBarContainerColor = snackBarContainerColor,
                                snackBarDuration = SnackbarDuration.Long
                            )
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RememberMeCheckBox { isChecked ->
                            viewModel.saveUserSessionFromPreferences(isChecked)
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    GameFilledButton(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        nameButton = stringResource(id = R.string.signup_btn_sign_up),
                        anErrorOccurred = viewModel.validationEmptyFields() || state.formHasErrors
                    ) {
                        viewModel.userAlreadyExists(
                            user = User(
                                name = state.name,
                                email = state.email,
                                password = state.password,
                                profileAvatarId = state.profileAvatarId
                            ),
                            snackBarContainerColor = snackBarContainerColorError,
                            snackBarBorderColor = snackBarBorderColorError,
                            isBiometricAvailable = isBiometricAvailable
                        )
                    }

                    GameOutlinedButton(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        nameButton = stringResource(id = R.string.signup_btn_cancel),
                        onClick = onNavigateBack
                    )
                }
            }
        }
    }
}