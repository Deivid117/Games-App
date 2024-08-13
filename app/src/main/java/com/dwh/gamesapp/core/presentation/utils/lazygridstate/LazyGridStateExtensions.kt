package com.dwh.gamesapp.core.presentation.utils.lazygridstate

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun LazyGridState.calculateDelayAndEasing(index: Int, columnCount: Int): Pair<Int, Easing> {
    val row = index / columnCount
    val column = index % columnCount
    val firstVisibleRow = remember { derivedStateOf { firstVisibleItemIndex / columnCount } }
    val visibleRows = layoutInfo.visibleItemsInfo.size / columnCount
    //val visibleRows = ceil(layoutInfo.visibleItemsInfo.size / columnCount.toDouble()).toInt() //TODO: Para uso de GridCells.Adaptive
    val scrollingToBottom = firstVisibleRow.value < row
    val isFirstLoad = visibleRows == 0
    val rowDelay = 200 * when {
        isFirstLoad -> row // initial load
        scrollingToBottom -> visibleRows + firstVisibleRow.value - row // scrolling to bottom
        else -> 1 // scrolling to top
    }
    val scrollDirectionMultiplier = if (scrollingToBottom || isFirstLoad) 1 else -1
    val columnDelay = column * 150 * scrollDirectionMultiplier
    val easing = if (scrollingToBottom || isFirstLoad) LinearOutSlowInEasing else FastOutSlowInEasing
    return rowDelay + columnDelay to easing
}

// TODO: Para uso de GridCells.Adaptive
@Composable
fun calculateColumns(state: LazyGridState, minItemWidth: Dp): Int {
    val layoutInfo = remember { derivedStateOf { state.layoutInfo } }
    val viewportWidth = layoutInfo.value.viewportEndOffset - layoutInfo.value.viewportStartOffset
    val minItemWidthPx = with(LocalDensity.current) { minItemWidth.toPx() }
    return maxOf(1, (viewportWidth / minItemWidthPx).toInt())
}