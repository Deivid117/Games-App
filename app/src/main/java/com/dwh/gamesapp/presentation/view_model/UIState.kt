package com.dwh.gamesapp.presentation.view_model

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val errorMessage: String) : UIState<Nothing>()
}