package com.dwh.gamesapp.games.presentation.components

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.presentation.theme.Dark_Green
import com.dwh.gamesapp.core.presentation.theme.Light_Green
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.presentation.utils.GameCustomException

@Composable
fun VerticalStaggeredGridGames(
    games: LazyPagingItems<Game>,
    listState: LazyStaggeredGridState,
    onShowSnackBar: (String) -> Unit,
    navigateToGameDetails: (Int) -> Unit
) {
    val metaCriticColor = if (isSystemInDarkTheme()) Light_Green else Dark_Green
    val loadState = games.loadState
    val error = when {
        loadState.append is LoadState.Error -> (loadState.append as? LoadState.Error)?.error
        loadState.refresh is LoadState.Error -> (loadState.refresh as? LoadState.Error)?.error
        else -> null
    }

    ShowInformationMessage(listIsEmpty = games.itemSnapshotList.isEmpty(), error = error)

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = listState,
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
                count = games.itemCount,
                key = games.itemKey { it.id ?: 0 }
            ) { index ->
                games[index]?.let { game ->
                    GameItem(game = game, metaCriticColor = metaCriticColor) {
                        navigateToGameDetails(game.id ?: 0)
                    }
                }
            }

            games.apply {
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

    ShowSnackBar(listIsNotEmpty = games.itemSnapshotList.isNotEmpty(), error = error, onShowSnackBar = onShowSnackBar)
}


@Composable
fun ShowInformationMessage(
    listIsEmpty: Boolean,
    error: Throwable?
) {
    if (listIsEmpty) {
        error?.let {
            if (it is GameCustomException) {
                InformationCard(
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