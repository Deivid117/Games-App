package com.dwh.gamesapp.genres_details.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.composables.PopularGameItemComposable
import com.dwh.gamesapp.genres.domain.model.GenreGame

@Composable
fun PopularGamesGenre(genreGames: List<GenreGame>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Popular Games",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(10.dp))

    ListPopularGames(genreGames)
}

@Composable
private fun ListPopularGames(genreGames: List<GenreGame>) {
    genreGames.forEach { game ->
        PopularGameItemComposable(
            gameName = game.name ?: "N/A",
            added = "${(game.added ?: 0)}"
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}