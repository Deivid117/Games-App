package com.dwh.gamesapp.a.presentation.ui.demo

/** la data class debe iniciar con el mismo nombre del viewmodel y de la ui **/
data class UiState(
    val value: String = "",
    val loading: Boolean = false
)
