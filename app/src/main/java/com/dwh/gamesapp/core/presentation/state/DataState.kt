package com.dwh.gamesapp.core.presentation.state

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<T>(val data: T, val code: Int? = null) : DataState<T>()
    data class Error(
        val errorMessage: String,
        val errorDescription: String = "",
        val code: Int? = null,
    ) : DataState<Nothing>()

    fun <R> mapper(transform: (T) -> R): DataState<R> = when (this) {
        is Success -> Success(data = transform(this.data), code = this.code)
        is Error -> Error(errorMessage = this.errorMessage, errorDescription = this.errorDescription, code = this.code)
        Loading -> Loading
    }
}