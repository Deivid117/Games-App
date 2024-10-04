package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dwh.gamesapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScaffold(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
    listState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    isTopAppBarVisible: Boolean = false,
    isBottomBarVisible: Boolean = true,
    isSnackBarVisible: Boolean = false,
    showTopAppBarColor: Boolean = false,
    showBackgroundGradient: Boolean = true,
    showSnackBarDismissAction: Boolean = false,
    isRefreshing: Boolean = false,
    isVisiblePullRefreshIndicator: Boolean = true,
    showSearchBar: Boolean = false,
    lottieAnimationSnackBar: Int = R.raw.broken_heart,
    snackBarContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    snackBarBorderColor: Color = MaterialTheme.colorScheme.error,
    snackBarLottieBackgroundColor: Color = Color.White,
    snackBarDuration: SnackbarDuration = SnackbarDuration.Short,
    topAppBarTitle: String = "",
    snackBarMessage: String = "",
    searchText: String = "",
    onSearchText: (String) -> Unit = {},
    onClickSearchGames: (String) -> Unit = {},
    onClickClearTextField: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onDismissSnackBar: () -> Unit = {},
    onRefresh: () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable() (BoxScope.() -> Unit) = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }

    HandleSnackBar(
        snackBarHostState = snackBarHostState,
        isSnackBarVisible = isSnackBarVisible,
        duration = snackBarDuration,
        onDismiss = onDismissSnackBar
    )

    Scaffold(
        topBar = {
            if (isTopAppBarVisible) {
                GameTopAppBar(
                    title = topAppBarTitle,
                    showTopAppBarColor = showTopAppBarColor,
                    scrollBehavior = scrollBehavior,
                    showSearchBar = showSearchBar,
                    listState = listState,
                    searchText = searchText,
                    onSearchText = { onSearchText(it) },
                    onClickSearchGames = { onClickSearchGames(it) },
                    onClickClearTextField = { onClickClearTextField() },
                    onBackClick = { onBackClick() }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                GameSnackBar(
                    snackBarMessages = snackBarMessage,
                    showSnackBarDismissAction = showSnackBarDismissAction,
                    lottieAnimation = lottieAnimationSnackBar,
                    containerColor = snackBarContainerColor,
                    borderColorSnackBar = snackBarBorderColor,
                    lottieBackgroundColor = snackBarLottieBackgroundColor,
                    onDismiss = onDismissSnackBar
                )
            }
        },
        bottomBar = { if (isBottomBarVisible) GameNavigationBar(navController) },
        floatingActionButton = { floatingActionButton() },
        content = { innerPadding ->
            if (showBackgroundGradient)
                GameBackgroundGradient(
                    modifier =
                        if (isTopAppBarVisible) modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                        else modifier,
                    paddingValues = innerPadding,
                    isRefreshing = isRefreshing,
                    isVisiblePullRefreshIndicator = isVisiblePullRefreshIndicator,
                    onRefresh = onRefresh,
                    content = content
                )
            else Box(modifier = Modifier.fillMaxSize()/*.padding(innerPadding)*/) { content() }
        }
    )
}

@Composable
private fun HandleSnackBar(
    snackBarHostState: SnackbarHostState,
    isSnackBarVisible: Boolean,
    duration: SnackbarDuration,
    onDismiss: () -> Unit
) {
    LaunchedEffect(isSnackBarVisible) {
        if (isSnackBarVisible) {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = "",
                duration = duration
            )

            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> {}
                SnackbarResult.Dismissed -> onDismiss()
            }
        }
    }
}