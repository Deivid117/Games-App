package com.dwh.gamesapp.games_details.presentation.utils

fun <T> generateStringFromList(list: List<T>?, mapper: (T) -> String): String {
    return list?.joinToString(", ") { mapper(it) } ?: "N/A"
}