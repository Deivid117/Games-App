package com.dwh.gamesapp.genres.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dwh.gamesapp.core.presentation.composables.GameCard

@Composable
fun GenreItem(
    modifier: Modifier,
    name: String,
    imageBackground: String,
    gamesCount: Int,
    onClick: () -> Unit
) {
    GameCard(
        modifier = modifier,
        name = name,
        imageBackground = imageBackground,
        gamesCount = gamesCount,
        onClick = onClick
    )
}