package com.dwh.gamesapp.games.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.domain.model.ScaleAndAlphaArgs
import com.dwh.gamesapp.core.presentation.theme.dark_green
import com.dwh.gamesapp.core.presentation.theme.light_green
import com.dwh.gamesapp.core.presentation.utils.animations.scaleAndAlpha
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.core.presentation.utils.lazystaggeredgridstate.calculateDelayAndEasing
import com.dwh.gamesapp.games.domain.model.Game

@Composable
fun VerticalStaggeredGridGames(
    wantedGames: List<Game>,
    listState: LazyStaggeredGridState,
    isLoading: Boolean,
    navigateToGameDetails: (Int) -> Unit
) {
    val metaCriticColor = if (isDarkThemeEnabled()) light_green else dark_green

    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        userScrollEnabled = !isLoading,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ) {
        if (isLoading) {
            items(count = 10) {
                val heightRandom = (80..150).random()
                GameShimmerItem(heightRandom)
            }
        } else {
            itemsIndexed(
                items = wantedGames,
                key = { _, game -> game.id ?: 0 }
            ) { index, game ->

                val (delay, easing) = listState.calculateDelayAndEasing(index = index, columnCount = 2)
                val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
                val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
                val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

                GameItem(
                    modifier = Modifier.graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                    game = game,
                    metaCriticColor = metaCriticColor,
                    navigateToGameDetails = { navigateToGameDetails(game.id ?: 0) }
                )
            }
        }
    }
}