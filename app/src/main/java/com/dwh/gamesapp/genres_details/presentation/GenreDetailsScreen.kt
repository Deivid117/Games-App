package com.dwh.gamesapp.genres_details.presentation

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.genres.domain.model.GameGenre
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.DescriptionComposable
import com.dwh.gamesapp.core.presentation.composables.CoverImageWithBackIconParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.ScrollingTitleComposable
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.a.presentation.composables.TopAppBarComposable
import com.dwh.gamesapp.core.presentation.composables.PopularGameItemComposable
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.utils.Constants.headerHeight
import com.dwh.gamesapp.utils.Constants.toolbarHeight
import com.dwh.gamesapp.utils.LifecycleOwnerListener

@Composable
fun GenreDetailsScreen(
    navController: NavController,
    genreId: Int,
    gamesGenre: ArrayList<GameGenre>,
    viewModel: GenreDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getGenreDetails(genreId)
    }

    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        GenreDetailsContent(viewModel, navController, gamesGenre)
    }
}

@Composable
private fun GenreDetailsContent(
    viewModel: GenreDetailsViewModel,
    navController: NavController,
    gamesGenre: ArrayList<GameGenre>
) {
    GenreDetailsValidateResponse(
        viewModel,
        navController,
        gamesGenre
    )
}

@Composable
private fun GenreDetailsValidateResponse(
    viewModel: GenreDetailsViewModel,
    navController: NavController,
    gamesGenre: ArrayList<GameGenre>
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UIState.Error -> {
            val errorMsg = (uiState as UIState.Error).errorMessage
            Log.e("GameDetailsScreenError", errorMsg)
            EmptyData(
                title = "Ocurrió un error",
                description = errorMsg
            )
        }

        UIState.Loading -> {
            LoadingAnimation(Modifier.fillMaxSize())
        }

        is UIState.Success -> {
            val genreDetails = (uiState as UIState.Success).data
            GenreDetailsContentWithParallaxEffect(navController, genreDetails, gamesGenre)
        }
    }
}

@Composable
private fun GenreDetailsContentWithParallaxEffect(
    navController: NavController,
    genreDetails: GenreDetails?,
    gamesGenre: ArrayList<GameGenre>
) {
    val scrollState = rememberScrollState()
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    LifecycleOwnerListener()

    Box(
        Modifier.background(MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        CoverImageWithBackIconParallaxEffect(
            scrollState = scrollState,
            imageUrl = genreDetails?.imageBackground ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { navController.popBackStack() }

        Box(Modifier.padding(top = 60.dp)) {
            GameGenreInformation(scrollState, genreDetails, gamesGenre)
        }

        /** TODO: Comprobar si es necesario dejar este composable
         * Si no eliminarlo */

        /*TopAppBarComposable(
            scrollState = scrollState,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
        ) { navController.popBackStack() }*/

        ScrollingTitleComposable(
            scrollState = scrollState,
            genreDetails?.name ?: "N/A"
        )
    }
}

@Composable
private fun GameGenreInformation(
    scrollState: ScrollState,
    genreDetails: GenreDetails?,
    gamesGenre: ArrayList<GameGenre>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(headerHeight - 50.dp))

        DescriptionComposable(genreDetails?.description ?: "N/A")

        Spacer(modifier = Modifier.height(15.dp))

        PopularGamesGenre(gamesGenre)
    }
}

@Composable
private fun PopularGamesGenre(gamesGenre: ArrayList<GameGenre>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Popular Games",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(10.dp))

    ListPopularGames(gamesGenre)
}

@Composable
private fun ListPopularGames(
    gamesGenre: ArrayList<GameGenre>
) {
    gamesGenre.forEach { game ->
        PopularGameItemComposable(
            gameName = game.name ?: "N/A",
            added = "${(game.added ?: 0)}"
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}

