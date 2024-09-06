package com.dwh.gamesapp.signup.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.ui.UiText

data class SignupState(
    val isLoading: Boolean = false,
    val isVisibleSuccessDialog: Boolean = false,
    val isVisibleAvatarsModalBottomSheet: Boolean = false,
    val isSnackBarVisible: Boolean = false,
    val formHasErrors: Boolean = false,
    val snackBarMessage: UiText? = null,
    val snackBarContainerColor: Color = Color.White,
    val snackBarBorderColor: Color = Color.White,
    val lottieAnimationSnackBar: Int = R.raw.broken_heart,
    val snackBarDuration: SnackbarDuration = SnackbarDuration.Short,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: UiText? = null,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val userId: Long = 0L,
    val profileAvatarId: Long = 0L,
    val profileAvatarImage: Int = R.drawable.user,
    val errorCode: Int? = 0,
    val errorMessage: String = "",
    val errorDescription: String = ""
)
