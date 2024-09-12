package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
    isTopAppBarVisible: Boolean = false,
    isBottomBarVisible: Boolean = true,
    isSnackBarVisible: Boolean = false,
    showTopAppBarColor: Boolean = false,
    showBackgroundGradient: Boolean = true,
    showSnackBarDismissAction: Boolean = false,
    lottieAnimationSnackBar: Int = R.raw.broken_heart,
    snackBarContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    snackBarBorderColor: Color = MaterialTheme.colorScheme.error,
    snackBarLottieBackgroundColor: Color = Color.White,
    snackBarDuration: SnackbarDuration = SnackbarDuration.Short,
    topAppBarTitle: String = "",
    snackBarMessage: String = "",
    onBackClick: () -> Unit = {},
    onDismissSnackBar: () -> Unit = {},
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
        bottomBar = { if (isBottomBarVisible) NavigationBarComposable(navController) },
        floatingActionButton = { floatingActionButton() },
        content = { innerPadding ->
            if (showBackgroundGradient)
                GameBackgroundGradient(
                    modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    paddingValues = innerPadding,
                    content = content
                )
            else Box { content() }
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