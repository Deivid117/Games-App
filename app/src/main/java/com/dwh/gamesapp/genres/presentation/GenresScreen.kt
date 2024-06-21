package com.dwh.gamesapp.genres.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.genres.domain.model.GenresResults
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.a.presentation.composables.CustomScaffold
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.CardItemComposable
import com.dwh.gamesapp.a.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.genres.domain.model.Genre

@Composable
fun GenresScreen(
    navController: NavController,
    genresViewModel: GenresViewModel = hiltViewModel()
) {
    LaunchedEffect(genresViewModel) {
        genresViewModel.getGenres()
    }

    CustomScaffold(
        navController,
        showTopBar = true,
        showBottomBar = false,
        showTopBarColor = true,
        title = "Genres",
        onBackClick = { navController.popBackStack() }
    ) {
        BackgroundGradient()
        GenresContent(navController, genresViewModel)
    }
}

@Composable
private fun GenresContent(
    navController: NavController,
    genresViewModel: GenresViewModel
) {
    Column(Modifier.fillMaxSize()) {
        GenresValidationResponse(navController, genresViewModel)
    }
}

@Composable
private fun GenresValidationResponse(
    navController: NavController,
    viewModel: GenresViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UIState.Error -> {
            val errorMsg = (uiState as UIState.Error).errorMessage
            Log.e("ERROR: GenresScreen", errorMsg)
            EmptyData(
                modifier = Modifier.fillMaxSize(),
                title = "Ocurrió un error",
                description = errorMsg
            )
        }

        UIState.Loading -> LoadingAnimation(Modifier.fillMaxSize())

        is UIState.Success -> {
            val genresResults = (uiState as UIState.Success).data
            GenresListContent(navController, genresResults)
        }
    }
}

@Composable
private fun GenresListContent(
    navController: NavController,
    genresResults: GenresResults?
) {
    val genres = genresResults?.results ?: arrayListOf()

    if (genres.isNotEmpty()) {
        GenresVerticalGrid(genres, navController)
    } else {
        EmptyData(
            modifier = Modifier.fillMaxSize(),
            title = "Sin información disponible",
            description = "No se han encontrado géneros por el momento, inténtelo más tarde"
        )
    }
}

@Composable
private fun GenresVerticalGrid(
    genres: List<Genre>,
    navController: NavController
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
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
    onClick: () -> Unit
) {
    CardItemComposable(
        onClick,
        imageBackground,
        name,
        gamesCount
    )
}