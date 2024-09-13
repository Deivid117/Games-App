package com.dwh.gamesapp.platforms.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dwh.gamesapp.core.presentation.composables.GameCard

@Composable
fun PlatformItem(
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