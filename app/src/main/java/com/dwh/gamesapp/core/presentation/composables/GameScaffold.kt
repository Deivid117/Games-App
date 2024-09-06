package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.presentation.composables.NavigationBarComposable
import com.dwh.gamesapp.a.presentation.composables.TopAppBarComposable

@Composable
fun GameScaffold(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    isTopBarVisible: Boolean = false,
    isBottomBarVisible: Boolean = true,
    isSnackBarVisible: Boolean = false,
    showTopBarColor: Boolean = false,
    showBackgroundGradient: Boolean = true,
    showSnackBarDismissAction: Boolean = false,
    lottieAnimationSnackBar: Int = R.raw.broken_heart,
    snackBarContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    snackBarBorderColor: Color = MaterialTheme.colorScheme.error,
    snackBarLottieBackgroundColor: Color = Color.White,
    snackBarDuration: SnackbarDuration = SnackbarDuration.Short,
    topBarTitle: String = "",
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
            if (isTopBarVisible) {
                TopAppBarComposable(
                    topBarTitle = topBarTitle,
                    topBarTitleColor = MaterialTheme.colorScheme.outlineVariant,
                    navigationIconColor = MaterialTheme.colorScheme.outlineVariant,
                    showTopBarColor = showTopBarColor,
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
                    modifier = modifier,
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