package com.dwh.gamesapp.a.presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun GameScaffold(
    navController: NavController,
    isBottomBarVisible: Boolean = true,
    isTopBarVisible: Boolean = false,
    showTopBarColor: Boolean = false,
    title: String = "",
    onBackClick: () -> Unit = {},
    content: @Composable() () -> Unit = {},
) {
    Scaffold(
        topBar = {
            if(isTopBarVisible) {
                TopAppBarComposable(
                    iconColor = MaterialTheme.colorScheme.outlineVariant,
                    showTopBarColor = showTopBarColor,
                    title = title,
                    titleColor = MaterialTheme.colorScheme.outlineVariant,
                    onClickNav = { onBackClick() }
            ) }
        },
        bottomBar = {
            if(isBottomBarVisible) NavigationBarComposable(navController)
        },
        content = { innerPadding ->
            BackgroundGradient(paddingValues = innerPadding) {
                content()
            }
            /*Box(modifier = Modifier.padding(innerPadding)) {
                BackgroundGradient()
                content()
            }*/
        }
    )
}