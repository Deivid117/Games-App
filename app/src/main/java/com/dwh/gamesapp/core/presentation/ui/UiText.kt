package com.dwh.gamesapp.core.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class StringValue(val value: String) : UiText()

    class StringResource(
        @StringRes val resourceId: Int,
        vararg val args: Any
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is StringValue -> value
            is StringResource -> stringResource(id = resourceId, formatArgs = args)
        }
    }
}