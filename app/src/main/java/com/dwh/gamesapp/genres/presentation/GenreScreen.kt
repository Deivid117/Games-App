package com.dwh.gamesapp.genres.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dwh.gamesapp.a.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.domain.model.ScaleAndAlphaArgs
import com.dwh.gamesapp.core.presentation.composables.CardItemComposable
import com.dwh.gamesapp.core.presentation.navigation.Screens.*
import com.dwh.gamesapp.core.presentation.utils.animations.scaleAndAlpha
import com.dwh.gamesapp.core.presentation.utils.lazygridstate.calculateDelayAndEasing
import com.dwh.gamesapp.genres.domain.model.Genre
import com.dwh.gamesapp.genres.domain.model.GenreGame

@Composable
fun GenreScreen(
    navController: NavController,
    viewModel: GenreViewModel,
    state: GenreState,
) {
    LaunchedEffect(viewModel) {
        viewModel.getGenres()
    }

    GameScaffold(
        navController = navController,
        isTopBarVisible = true,
        isBottomBarVisible = false,
        showTopBarColor = true,
        topBarTitle = "Genres",
        onBackClick = { navController.popBackStack() }
    ) {
        if (state.isLoading) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            GenreView(navController = navController, state = state) { viewModel.setGenreGames(it) }
        }
    }
}

@Composable
private fun GenreView(
    navController: NavController,
    state: GenreState,
    onGenreClick: (List<GenreGame>) -> Unit
) {
    if (state.genres.isNotEmpty()) {
        VerticalGridGenres(
            navController = navController,
            genres = state.genres,
            onGenreClick = onGenreClick
        )
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
    onGenreClick: (List<GenreGame>) -> Unit
) {
    //val columnsSize = 150.dp //TODO: Para uso de GridCells.Adaptive
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = genres,
            key = { _, genre -> genre.id ?: 0 }
        ) { index, genre ->

            //val columns = calculateColumns(listState, columnsSize) //TODO: Para uso de GridCells.Adaptive
            val (delay, easing) = listState.calculateDelayAndEasing(index = index, columnCount = 2)
            val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
            val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
            val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

            GenreItem(
                modifier = Modifier.graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                name = genre.name ?: "N/A",
                imageBackground = genre.imageBackground ?: "",
                gamesCount = genre.gamesCount ?: 0
            ) {
                onGenreClick(genre.games)
                navController.navigate(GENRE_DETAILS_SCREEN.name + "/" + (genre.id ?: 0))
            }
        }
    }
}

@Composable
private fun GenreItem(
    modifier: Modifier,
    name: String,
    imageBackground: String,
    gamesCount: Int,
    onClick: () -> Unit,
) {
    CardItemComposable(
        modifier = modifier,
        name = name,
        imageBackground = imageBackground,
        gamesCount = gamesCount,
        onClick = onClick
    )
}