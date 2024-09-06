package com.dwh.gamesapp.a.domain.model.user

data class User(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val profileAvatarId: Long = 0
)