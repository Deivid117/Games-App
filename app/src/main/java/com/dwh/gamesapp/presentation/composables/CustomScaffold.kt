package com.dwh.gamesapp.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun CustomScaffold(
    navController: NavController,
    showBottomBar: Boolean = true,
    showTopBar: Boolean = false,
    showTopBarColor: Boolean = false,
    title: String = "",
    onBackClick: () -> Unit = {},
    content: @Composable() () -> Unit = {},
) {
    Scaffold(
        topBar = {
            if(showTopBar) {
                TopAppBarComposable(
                    iconColor = MaterialTheme.colorScheme.background,
                    showTopBarColor = showTopBarColor,
                    title = title,
                    onClickNav = { onBackClick() }
            ) }
        },
        bottomBar = {
            if(showBottomBar) NavigationBarComposable(navController)
        },
        content = {
                innerPadding ->
            // Apply the padding globally to the whole BottomNavScreensController
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    )
}