package com.dwh.gamesapp.games_details.presentation.utils

import kotlinx.datetime.LocalDate

object DateFormatter {
    fun formattedDate(date: String): String {
        return if (date.isNotEmpty()) {
            val outputFormatter = { localDate: LocalDate ->
                val month = localDate.month.name.lowercase().replaceFirstChar { it.uppercase() }.substring(0, 3)
                val day = localDate.dayOfMonth
                val year = localDate.year
                "$month $day, $year"
            }

            val dateFormatted = LocalDate.parse(date)
            outputFormatter(dateFormatted)
        } else { "N/A" }
    }
}