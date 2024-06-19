package com.dwh.gamesapp.a.presentation.ui.demo

sealed class DemoUiState {
    object Loading: DemoUiState()
    data class Success(
        val value: String = ""
    ): DemoUiState()
    data class Error(val msg: String): DemoUiState()
}

/*
sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
}*/

/** PARA REFERENCIAS **/
/*sealed class NetworkResult<T>(
    val data: T? = null,
    val message : String? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T> : NetworkResult<T>()
}*/
