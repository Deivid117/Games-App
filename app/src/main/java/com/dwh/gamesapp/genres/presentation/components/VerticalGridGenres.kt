package com.dwh.gamesapp.genres.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.core.domain.model.ScaleAndAlphaArgs
import com.dwh.gamesapp.core.presentation.composables.PlatformAndGenreShimmerItem
import com.dwh.gamesapp.core.presentation.utils.animations.scaleAndAlpha
import com.dwh.gamesapp.core.presentation.utils.lazygridstate.calculateDelayAndEasing
import com.dwh.gamesapp.genres.domain.model.Genre
import com.dwh.gamesapp.genres.domain.model.GenreGame

@Composable
fun VerticalGridGenres(
    genres: List<Genre>,
    isLoading: Boolean,
    navigateToGenreDetails: (Int) -> Unit,
    onGenreClick: (List<GenreGame>) -> Unit
) {
    //val columnsSize = 150.dp //TODO: Para uso de GridCells.Adaptive
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        userScrollEnabled = !isLoading,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isLoading) {
            items(count = 10, itemContent = { PlatformAndGenreShimmerItem() })
        } else {
            itemsIndexed(
                items = genres,
                key = { _, genre -> genre.id ?: 0 }
            ) { index, genre ->

                //val columns = calculateColumns(listState, columnsSize) //TODO: Para uso de GridCells.Adaptive
                val (delay, easing) = listState.calculateDelayAndEasing(index = index, columnCount = 2)
                val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
                val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
                val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

                GenreItem(
                    modifier = Modifier.graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                    name = genre.name ?: "N/A",
                    imageBackground = genre.imageBackground ?: "",
                    gamesCount = genre.gamesCount ?: 0
                ) {
                    onGenreClick(genre.games)
                    navigateToGenreDetails(genre.id ?: 0)
                }
            }
        }
    }
}