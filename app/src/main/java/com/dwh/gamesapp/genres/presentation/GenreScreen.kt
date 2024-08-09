package com.dwh.gamesapp.genres.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dwh.gamesapp.a.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.CardItemComposable
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.genres.domain.model.Genre

@Composable
fun GenreScreen(
    navController: NavController,
    genreViewModel: GenreViewModel,
    state: GenreState,
) {
    LaunchedEffect(genreViewModel) {
        genreViewModel.getGenres()
    }

    GameScaffold(
        navController,
        isTopBarVisible = true,
        isBottomBarVisible = false,
        showTopBarColor = true,
        title = "Genres",
        onBackClick = { navController.popBackStack() }
    ) {
        if (state.isLoading) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            GenreView(navController, state)
        }
    }
}

@Composable
private fun GenreView(
    navController: NavController,
    state: GenreState,
) {
    if (state.genres.isNotEmpty()) {
        VerticalGridGenres(navController = navController, genres = state.genres)
    } else {
        InformationCard(
            modifier = Modifier.fillMaxSize(),
            message = state.errorMessage,
            description = state.errorDescription
        )
    }
}

@Composable
private fun VerticalGridGenres(
    navController: NavController,
    genres: List<Genre>,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            GenreItem(
                name = genre.name ?: "N/A",
                imageBackground = genre.imageBackground ?: "",
                gamesCount = genre.gamesCount ?: 0
            ) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "games",
                    genre.games
                )
                navController.navigate(Screens.GENRES_DETAILS_SCREEN + "/" + (genre.id ?: 0))
            }
        }
    }
}

@Composable
private fun GenreItem(
    name: String,
    imageBackground: String,
    gamesCount: Int,
    onClick: () -> Unit,
) {
    CardItemComposable(
        name = name,
        imageBackground = imageBackground,
        gamesCount = gamesCount,
        onClick = onClick
    )
}