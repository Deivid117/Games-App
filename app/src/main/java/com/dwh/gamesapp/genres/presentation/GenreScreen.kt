@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.dwh.gamesapp.genres.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.genres.domain.model.GenreGame
import com.dwh.gamesapp.genres.presentation.components.VerticalGridGenres

@Composable
fun GenreScreen(
    navController: NavController,
    viewModel: GenreViewModel,
    state: GenreState,
    navigateToGenreDetails: (Int) -> Unit
) {
    GameScaffold(
        navController = navController,
        isTopAppBarVisible = true,
        isBottomBarVisible = false,
        showTopAppBarColor = true,
        isRefreshing = state.isRefreshing,
        topAppBarTitle = "Genres",
        onRefresh = { viewModel.refreshGenres() },
        onBackClick = { navController.popBackStack() }
    ) {
        GenreView(
            state = state,
            navigateToGenreDetails = navigateToGenreDetails,
            onGenreClick = { viewModel.setGenreGames(it) }
        )
    }
}

@Composable
private fun GenreView(
    state: GenreState,
    navigateToGenreDetails: (Int) -> Unit,
    onGenreClick: (List<GenreGame>) -> Unit
) {
    when {
        (!state.isLoading && state.genres.isEmpty()) -> {
            GameInformationalMessageCard(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                message = state.errorMessage,
                description = state.errorDescription
            )
        }
        else -> {
            VerticalGridGenres(
                genres = state.genres,
                isLoading = state.isLoading,
                navigateToGenreDetails = navigateToGenreDetails,
                onGenreClick = onGenreClick
            )
        }
    }
}