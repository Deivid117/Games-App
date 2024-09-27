package com.dwh.gamesapp.games.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.dwh.gamesapp.core.domain.model.ScaleAndAlphaArgs
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.presentation.theme.dark_green
import com.dwh.gamesapp.core.presentation.theme.light_green
import com.dwh.gamesapp.core.presentation.utils.animations.scaleAndAlpha
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.core.presentation.utils.lazystaggeredgridstate.calculateDelayAndEasing
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.presentation.utils.GameCustomException

@Composable
fun VerticalStaggeredGridPaginatedGames(
    paginatedGames: LazyPagingItems<Game>,
    listState: LazyStaggeredGridState,
    onShowSnackBar: (String) -> Unit,
    navigateToGameDetails: (Int) -> Unit
) {
    val metaCriticColor = if (isDarkThemeEnabled()) light_green else dark_green
    val loadState = paginatedGames.loadState
    val error = when {
        loadState.append is LoadState.Error -> (loadState.append as? LoadState.Error)?.error
        loadState.refresh is LoadState.Error -> (loadState.refresh as? LoadState.Error)?.error
        else -> null
    }

    ShowInformationMessage(listIsEmpty = paginatedGames.itemSnapshotList.isEmpty(), error = error)

    LazyVerticalStaggeredGrid(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ) {
        if (loadState.refresh is LoadState.Loading) {
            items(count = 10) {
                val heightRandom = (80..150).random()
                GameShimmerItem(heightRandom)
            }
        } else {
            items(
                count = paginatedGames.itemCount,
                key = paginatedGames.itemKey { it.id ?: 0 }
            ) { index ->
                paginatedGames[index]?.let { game ->

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


            paginatedGames.apply {
                item(span = StaggeredGridItemSpan.FullLine) {
                    if (loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            GameLoadingAnimation(size = 200)
                        }
                    }
                }
            }
        }
    }

    ShowSnackBar(listIsNotEmpty = paginatedGames.itemSnapshotList.isNotEmpty(), error = error, onShowSnackBar = onShowSnackBar)
}


@Composable
fun ShowInformationMessage(
    listIsEmpty: Boolean,
    error: Throwable?
) {
    if (listIsEmpty) {
        error?.let {
            if (it is GameCustomException) {
                GameInformationalMessageCard(
                    modifier = Modifier.fillMaxSize(),
                    message = it.gameErrorMessage.errorMessage,
                    description = it.gameErrorMessage.errorDescription
                )
            }
        }
    }
}

@Composable
fun ShowSnackBar(
    listIsNotEmpty: Boolean,
    error: Throwable?,
    onShowSnackBar: (String) -> Unit
) {
    if (listIsNotEmpty)
        error?.let {
            if (it is GameCustomException) {
                LaunchedEffect(Unit) {
                    onShowSnackBar((it.gameErrorMessage.errorDescription))
                }
            }
        }
}