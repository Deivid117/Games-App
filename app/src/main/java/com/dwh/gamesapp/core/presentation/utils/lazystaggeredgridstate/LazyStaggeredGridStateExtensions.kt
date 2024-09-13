package com.dwh.gamesapp.core.presentation.utils.lazystaggeredgridstate

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun LazyStaggeredGridState.calculateDelayAndEasing(index: Int, columnCount: Int): Pair<Int, Easing> {
    val row = index / columnCount
    val column = index % columnCount
    val firstVisibleRow = remember { derivedStateOf { firstVisibleItemIndex / columnCount } }
    val visibleRows = layoutInfo.visibleItemsInfo.size / columnCount
    val scrollingToBottom = firstVisibleRow.value < row
    val isFirstLoad = visibleRows == 0

    val rowDelay = 200 * when {
        isFirstLoad -> row
        scrollingToBottom -> visibleRows + firstVisibleRow.value - row
        else -> 1
    }

    val scrollDirectionMultiplier = if (scrollingToBottom || isFirstLoad) 1 else -1
    val columnDelay = column * 150 * scrollDirectionMultiplier

    val easing = if (scrollingToBottom || isFirstLoad) LinearOutSlowInEasing else FastOutSlowInEasing

    return rowDelay + columnDelay to easing
}