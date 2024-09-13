@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.dwh.gamesapp.platforms.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.platforms.presentation.components.VerticalGridPlatforms

@Composable
fun PlatformScreen(
    navController: NavController,
    viewModel: PlatformViewModel,
    state: PlatformState,
    navigateToPlatformDetails: (Int) -> Unit
) {
    GameScaffold(
        navController = navController,
        isTopAppBarVisible = true,
        isBottomBarVisible = false,
        showTopAppBarColor = true,
        isRefreshing = state.isRefreshing,
        topAppBarTitle = "Platforms",
        onRefresh = { viewModel.refreshPlatforms() },
        onBackClick = { navController.popBackStack() }
    ) {
        PlatformView(
            state = state,
            navigateToPlatformDetails = navigateToPlatformDetails,
            onPlatformClick = { viewModel.setPlatformGames(it) }
        )
    }
}

@Composable
private fun PlatformView(
    state: PlatformState,
    navigateToPlatformDetails: (Int) -> Unit,
    onPlatformClick: (List<PlatformGame>) -> Unit
) {
    when {
        (!state.isLoading && state.platforms.isEmpty()) -> {
            GameInformationalMessageCard(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                message = state.errorMessage,
                description = state.errorDescription
            )
        }
        else -> {
            VerticalGridPlatforms(
                platforms = state.platforms,
                isLoading = state.isLoading,
                navigateToPlatformDetails = navigateToPlatformDetails,
                onPlatformClick = onPlatformClick
            )
        }
    }
}