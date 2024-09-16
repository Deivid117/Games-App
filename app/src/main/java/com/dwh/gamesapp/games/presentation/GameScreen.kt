@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.games.presentation

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.dwh.gamesapp.R
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.theme.dark_snackbar_container_warning
import com.dwh.gamesapp.core.presentation.theme.snackbar_border_warning
import com.dwh.gamesapp.core.presentation.theme.light_snackbar_container_warning
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.games.presentation.components.GameButtonToScrollToTop
import com.dwh.gamesapp.games.presentation.components.VerticalStaggeredGridGames

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    state: GameState,
    games: LazyPagingItems<Game>,
    navigateToGameDetails: (Int) -> Unit
) {
    val listState = rememberLazyStaggeredGridState()

    GameScaffold(
        navController = navController,
        isSnackBarVisible = state.isSnackBarVisible,
        showSnackBarDismissAction = false,
        isRefreshing = state.isRefreshing,
        snackBarMessage = state.snackBarMessage,
        lottieAnimationSnackBar = R.raw.error_not_found,
        snackBarBorderColor = snackbar_border_warning,
        snackBarContainerColor =
            if (isDarkThemeEnabled()) dark_snackbar_container_warning
            else light_snackbar_container_warning,
        floatingActionButton = {
            GameButtonToScrollToTop(
                listState = listState,
                listIsNotEmpty = games.itemCount > 0
            )
        },
        onRefresh = { viewModel.refreshList { games.refresh() } },
        onDismissSnackBar = { viewModel.hideSnackBar() }
    ) {
        GameView(
            games = games,
            listState = listState,
            onShowSnackBar = { viewModel.showSnackBar(it) },
            navigateToGameDetails = navigateToGameDetails
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