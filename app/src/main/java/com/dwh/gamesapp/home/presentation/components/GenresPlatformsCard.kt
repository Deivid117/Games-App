package com.dwh.gamesapp.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dwh.gamesapp.R

@Composable
fun GenresPlatformsCard(
    navigateToGenres: () -> Unit,
    navigateToPlatforms: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeItemCard(name = "Géneros", imageBackground = R.drawable.genres_games, onClick = navigateToGenres)

        HomeItemCard(name = "Plataformas", imageBackground = R.drawable.platforms_collage, onClick = navigateToPlatforms)
    }
}