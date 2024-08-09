package com.dwh.gamesapp.core.data

import com.dwh.gamesapp.core.presentation.state.DataState

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

inline fun <T, R> DataState<T>.map2(transform: (T) -> R): DataState<R> {
    return when (this) {
        is DataState.Success -> {
            val transformedData = data?.let(transform)
            if (transformedData != null) {
                DataState.Success(transformedData, code ?: 0)
            } else {
                DataState.Error(errorMessage = "Mapping error: unable to map data", code =  code)
            }
        }
        is DataState.Error -> DataState.Error(errorMessage = "Error desconocido", code = code)
        is DataState.Loading -> TODO()
    }
}