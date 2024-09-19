package com.dwh.gamesapp.core.domain.model

data class User(
    val id: Long = 0,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val profileAvatarId: Long = 0,
    val fingerPrintToken: EncryptedData? = null
)