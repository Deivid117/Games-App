package com.dwh.gamesapp.presentation.ui.genres

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
import com.dwh.gamesapp.domain.model.genres.GenresResults
import com.dwh.gamesapp.presentation.composables.*
import com.dwh.gamesapp.presentation.view_model.genres.GenresUiState
import com.dwh.gamesapp.presentation.view_model.genres.GenresViewModel
import com.dwh.gamesapp.presentation.navigation.Screens

@Composable
fun GenresScreen(
    navController: NavController,
    viewModel: GenresViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getAllGenres()
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
        GenresValidationResponse(navController, viewModel)
    }
}

@Composable
fun GenresValidationResponse(
    navController: NavController,
    viewModel: GenresViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is GenresUiState.Error -> {
            val erroMsg = (uiState as GenresUiState.Error).errorMessage
            Log.e("GameDetailsScreenError", erroMsg)
            EmptyData(
                title = "Sin información disponible",
                description = "No se han encontrado géneros por el momento, inténtelo más tarde"
            )
        }
        GenresUiState.Loading -> {
            LoadingAnimation()
        }
        is GenresUiState.Success -> {
            val data = (uiState as GenresUiState.Success).data
            GenresContent(navController, data)
        }
    }
}

@Composable
fun GenresContent(
    navController: NavController,
    genres: List<GenresResults>
) {
    if(genres.isNotEmpty()) {
        GenresList(genres, navController)
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No se han encontrado juegos por el momento, inténtelo más tarde"
        )
    }
}

@Composable
fun GenresList(genres: List<GenresResults>, navController: NavController) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(genres) {
            GenreItem(it.name, it.imageBackground, it.gamesCount) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "games",
                    it.games
                )
                navController.navigate(Screens.GENRES_DETAILS_SCREEN + "/" + it.id)
            }
        }
    }
}

@Composable
fun GenreItem(name: String, imageBackground: String, gamesCount: Int, onClick: () -> Unit) {
    VerticalGridItem(onClick, imageBackground, name, gamesCount)
}

