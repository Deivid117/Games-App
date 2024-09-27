@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.games.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.theme.dark_snackbar_container_warning
import com.dwh.gamesapp.core.presentation.theme.snackbar_border_warning
import com.dwh.gamesapp.core.presentation.theme.light_snackbar_container_warning
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.games.presentation.components.GameButtonToScrollToTop
import com.dwh.gamesapp.games.presentation.components.VerticalStaggeredGridGames
import com.dwh.gamesapp.games.presentation.components.VerticalStaggeredGridPaginatedGames

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    state: GameState,
    paginatedGames: LazyPagingItems<Game>,
    navigateToGameDetails: (Int) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val listState = rememberLazyStaggeredGridState()

    GameScaffold(
        navController = navController,
        scrollBehavior = scrollBehavior,
        isSnackBarVisible = state.isSnackBarVisible,
        isTopAppBarVisible = true,
        showTopAppBarColor = true,
        showSnackBarDismissAction = false,
        isVisiblePullRefreshIndicator = state.wantedGames.isEmpty(),
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
                listIsNotEmpty = paginatedGames.itemCount > 0,
                scrollBehavior = scrollBehavior
            )
        },
        listState = listState,
        showSearchBar = true,
        searchText = state.searchText,
        onSearchText = { viewModel.setSearchText(it) },
        onClickSearchGames = { viewModel.searchGames(it) },
        onClickClearTextField = { viewModel.clearValues() },
        onRefresh = { viewModel.refreshList { paginatedGames.refresh() } },
        onDismissSnackBar = { viewModel.hideSnackBar() }
    ) {
        GameView(
            paginatedGames = paginatedGames,
            wantedGames = state.wantedGames,
            listState = listState,
            isLoading = state.isLoading,
            isError = state.isError,
            errorMessage = state.errorMessage,
            errorDescription = state.errorDescription,
            searchText = state.searchText,
            onShowSnackBar = { viewModel.showSnackBar(it) },
            navigateToGameDetails = navigateToGameDetails
        )
    }
}

@Composable
private fun GameView(
    paginatedGames: LazyPagingItems<Game>,
    wantedGames: List<Game>,
    listState: LazyStaggeredGridState,
    isLoading: Boolean,
    isError: Boolean,
    errorMessage: String,
    errorDescription: String,
    searchText: String,
    onShowSnackBar: (String) -> Unit,
    navigateToGameDetails: (Int) -> Unit
) {
    when {
        searchText.isEmpty() -> {
            VerticalStaggeredGridPaginatedGames(
                paginatedGames = paginatedGames,
                listState = listState,
                onShowSnackBar = onShowSnackBar,
                navigateToGameDetails = navigateToGameDetails
            )
        }

        (isLoading || wantedGames.isNotEmpty()) -> {
            VerticalStaggeredGridGames(
                wantedGames = wantedGames,
                listState = listState,
                isLoading = isLoading,
                navigateToGameDetails = navigateToGameDetails
            )
        }

        isError -> {
            GameInformationalMessageCard(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                message = errorMessage,
                description = errorDescription
            )
        }
    }
}