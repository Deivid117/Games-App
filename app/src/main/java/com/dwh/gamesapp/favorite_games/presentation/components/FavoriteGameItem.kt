package com.dwh.gamesapp.favorite_games.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame

@Composable
fun FavoriteGameItem(
    modifier: Modifier = Modifier,
    favoriteGame: FavoriteGame,
    metaCriticColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(favoriteGame.background_image)
                    .crossfade(500)
                    .build(),
                contentDescription = "game cover",
                placeholder = painterResource(id = R.drawable.image_controller_placeholder),
                error = painterResource(id = R.drawable.image_unavailable_error),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = favoriteGame.released,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        modifier = Modifier
                            .border(1.dp, metaCriticColor, shape = RoundedCornerShape(5.dp))
                            .padding(3.dp),
                        text = "${favoriteGame.metacritic}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = metaCriticColor
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = favoriteGame.name, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}