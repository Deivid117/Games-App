package com.dwh.gamesapp.a.presentation.composables

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
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameSnackBar

@Composable
fun GameScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    isTopBarVisible: Boolean = false,
    isBottomBarVisible: Boolean = true,
    isSnackBarVisible: Boolean = false,
    showTopBarColor: Boolean = false,
    topBarTitle: String = "",
    snackBarMessage: String = "",
    onBackClick: () -> Unit = {},
    onDismissSnackBar: () -> Unit = {},
    onActionSnackBar: () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable() (BoxScope.() -> Unit) = {},
) {
    val snackBarHostState = remember { SnackbarHostState() }

    HandleSnackBar(
        snackBarHostState = snackBarHostState,
        isSnackBarVisible = isSnackBarVisible,
        onDismiss = onDismissSnackBar,
        onAction = onActionSnackBar
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
                GameSnackBar(snackBarMessage) { onActionSnackBar() }
            }
        },
        bottomBar = { if (isBottomBarVisible) NavigationBarComposable(navController) },
        floatingActionButton = { floatingActionButton() },
        content = { innerPadding ->
            BackgroundGradient(
                modifier = modifier,
                paddingValues = innerPadding,
                content = content
            )
        }
    )
}

@Composable
private fun HandleSnackBar(
    snackBarHostState: SnackbarHostState,
    isSnackBarVisible: Boolean,
    onAction: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    LaunchedEffect(isSnackBarVisible) {
        if (isSnackBarVisible) {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = "",
                duration = SnackbarDuration.Short
            )
            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> onAction()
                SnackbarResult.Dismissed -> onDismiss()
            }
        }
    }
}


