package com.dwh.gamesapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.core.presentation.utils.modifier.vertical
import com.dwh.gamesapp.home.domain.model.GameItem
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun HorizontalListGames(
    games: List<GameItem>,
    title: String,
    isLoading: Boolean,
    navigateToGameDetails: (Int) -> Unit
) {
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background.copy(0.5f))
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(
            state = scrollState,
            flingBehavior = rememberSnapperFlingBehavior(scrollState),
            userScrollEnabled = !isLoading,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item { GamesScrollBuffer(title) }

            if (isLoading) items(count = 10, itemContent = { HomeShimmerItem() })
            else {
                items(games) { game ->
                    GameItem(game = game, navigateToGameDetails = navigateToGameDetails)
                }
            }

            item { GamesScrollBuffer(title) }
        }
    }
}

@Composable
fun GamesScrollBuffer(title: String) {
    Card(
        modifier = Modifier
            .height(220.dp)
            .width(90f.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .vertical()
                    .rotate(-90f),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Color.White,
                maxLines = 2
            )
        }
    }
}