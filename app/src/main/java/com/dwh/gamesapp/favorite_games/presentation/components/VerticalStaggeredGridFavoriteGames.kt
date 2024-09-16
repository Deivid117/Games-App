package com.dwh.gamesapp.favorite_games.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame

@Composable
fun VerticalStaggeredGridFavoriteGames(
    favoriteGames: List<FavoriteGame>,
    navigateToGameDetails: (Int) -> Unit
) {
    val listState = rememberLazyStaggeredGridState()
    val metaCriticColor = if(isDarkThemeEnabled()) light_green else dark_green

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ){
        itemsIndexed(
            items = favoriteGames,
            key = { _, item ->  item.id }
        ){ index, game ->
            val (delay, easing) = listState.calculateDelayAndEasing(index = index, columnCount = 2)
            val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
            val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
            val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

            FavoriteGameItem(
                modifier = Modifier.graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                favoriteGame = game,
                metaCriticColor = metaCriticColor
            ) {
                navigateToGameDetails(game.id)
            }
        }
    }
}