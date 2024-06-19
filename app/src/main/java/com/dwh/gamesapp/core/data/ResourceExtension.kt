package com.dwh.gamesapp.core.data

inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> {
            val transformedData = data?.let(transform)
            if (transformedData != null) {
                Resource.Success(transformedData, code ?: 0)
            } else {
                Resource.Error("Mapping error: unable to map data", code)
            }
        }
        is Resource.Error -> Resource.Error(message ?: "Error desconocido", code)
        is Resource.Loading -> TODO()
    }
}