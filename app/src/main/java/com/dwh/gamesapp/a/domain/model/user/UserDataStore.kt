package com.dwh.gamesapp.a.domain.model.user

data class UserDataStore(
    val id: Int = 0,
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String? = null,
    val isLogged: Boolean = false,
    val imageId: Int = 0
)