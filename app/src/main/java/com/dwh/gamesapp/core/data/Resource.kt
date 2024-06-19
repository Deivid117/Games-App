package com.dwh.gamesapp.core.data

sealed class Resource<T>(
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T, code: Int) : Resource<T>(data = data, code = code)

    class Error<T>(message: String, code: Int? = null) : Resource<T>(message = message, code = code)

    class Loading<T> : Resource<T>()
}