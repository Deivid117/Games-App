package com.dwh.gamesapp.platforms_details.presentation

import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails

data class PlatformDetailsState(
    val isLoading: Boolean = false,
    val platformDetails: PlatformDetails? = PlatformDetails(),
    val errorMessage: String = "",
    val errorDescription: String = "",
    val errorCode: Int? = 0
)