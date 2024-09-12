package com.dwh.gamesapp.profile.presentation

import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val isVisibleLogoutDialog: Boolean = false,
    val user: User = User(),
    val userId: Long = 0L,
    val userProfileAvatar: Int = R.drawable.user,
    val errorCode: Int? = 0,
    val errorMessage: String = "",
    val errorDescription: String = ""
)
