package com.dwh.gamesapp.games.presentation

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.games.presentation.components.GameButtonToScrollToTop
import com.dwh.gamesapp.games.presentation.components.VerticalStaggeredGridGames

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    state: GameState,
    games: LazyPagingItems<Game>,
    navigateToGameDetails: (Int) -> Unit
) {
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = state.isRefreshing,
            onRefresh = { viewModel.refreshList { games.refresh() } })
    val listState = rememberLazyStaggeredGridState()

    GameScaffold(
        navController = navController,
        modifier = Modifier.pullRefresh(pullRefreshState),
        isSnackBarVisible = state.isSnackBarVisible,
        snackBarMessage = state.snackBarMessage,
        floatingActionButton = {
            GameButtonToScrollToTop(
                listState = listState,
                listIsNotEmpty = games.itemCount > 0
            )
        },
        onDismissSnackBar = { viewModel.hideSnackBar() }
    ) {
        GameView(
            games = games,
            listState = listState,
            onShowSnackBar = { viewModel.showSnackBar(it) },
            navigateToGameDetails = navigateToGameDetails
        )

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isRefreshing,
            state = pullRefreshState
        )
    }
}

@Composable
private fun GameView(
    games: LazyPagingItems<Game>,
    listState: LazyStaggeredGridState,
    onShowSnackBar: (String) -> Unit,
    navigateToGameDetails: (Int) -> Unit
) {
    VerticalStaggeredGridGames(
        games = games,
        listState = listState,
        onShowSnackBar = onShowSnackBar,
        navigateToGameDetails = navigateToGameDetails
    )
}