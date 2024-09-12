package com.dwh.gamesapp.edit_profile.presentation

import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.ui.UiText

data class EditProfileState(
    val isLoading: Boolean = false,
    val isVisibleSuccessDialog: Boolean = false,
    val isVisibleAvatarsModalBottomSheet: Boolean = false,
    val formHasErrors: Boolean = false,
    val userId: Long = 0L,
    val profileAvatarId: Long = 0L,
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
    val userProfileAvatar: Int = R.drawable.user,
    val errorCode: Int? = 0,
    val errorMessage: String = "",
    val errorDescription: String = ""
)