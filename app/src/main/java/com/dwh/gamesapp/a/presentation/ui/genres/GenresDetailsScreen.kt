package com.dwh.gamesapp.a.presentation.ui.genres

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.domain.model.genre_details.GenreDetails
import com.dwh.gamesapp.a.domain.model.genres.GenreGames
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.a.presentation.composables.DetailsDescription
import com.dwh.gamesapp.a.presentation.composables.DetailsHeader
import com.dwh.gamesapp.a.presentation.composables.DetailsTitle
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LifecycleOwnerListener
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.a.presentation.composables.TopAppBarComposable
import com.dwh.gamesapp.a.presentation.view_model.genre_details.GenreDetailsUiState
import com.dwh.gamesapp.a.presentation.view_model.genre_details.GenreDetailsViewModel
import com.dwh.gamesapp.utils.Constants.headerHeight
import com.dwh.gamesapp.utils.Constants.toolbarHeight

@Composable
fun GenreDetailsScreen(
    navController: NavController,
    genredId: Int,
    games: ArrayList<GenreGames>,
    viewModel: GenreDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getGenreDetails(genredId)
    }

    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        GenreDetailsValidateResponse(viewModel, navController, games)
    }

}

@Composable
fun GenreDetailsValidateResponse(
    viewModel: GenreDetailsViewModel,
    navController: NavController,
    games: ArrayList<GenreGames>
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is GenreDetailsUiState.Error -> {
            val erroMsg = (uiState as GenreDetailsUiState.Error).errorMessage
            Log.e("GameDetailsScreenError", erroMsg)
            EmptyData(
                title = "Sin información disponible",
                description = "No se han encontrado detalles del género por el momento, inténtelo más tarde"
            )
        }
        GenreDetailsUiState.Loading -> {
            LoadingAnimation()
        }
        is GenreDetailsUiState.Success -> {
            val data = (uiState as GenreDetailsUiState.Success).data
            GenreDetailsContent(navController, data, games)
        }
    }
}

@Composable
fun GenreDetailsContent(
    navController: NavController,
    genreDetails: GenreDetails,
    games: ArrayList<GenreGames>
) {
    val scrollState = rememberScrollState()
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    LifecycleOwnerListener()

    Box(
        Modifier.background(MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        DetailsHeader(
            scrollState = scrollState,
            url = genreDetails.imageBackground,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { navController.popBackStack() }

        Box(Modifier.padding(top = 60.dp)) {
            GenreBody(scrollState, genreDetails, games)
        }

        TopAppBarComposable(
            scrollState = scrollState,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
        ) { navController.popBackStack() }

        DetailsTitle(scrollState = scrollState, genreDetails.name)
    }
}

@Composable
fun GenreBody(scrollState: ScrollState, genreDetails: GenreDetails, games: ArrayList<GenreGames>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(headerHeight - 50.dp))

        DetailsDescription(genreDetails.description)
        
        Spacer(modifier = Modifier.height(15.dp))

        PopularGamesGenre(games)
        
    }
}

@Composable
private fun PopularGamesGenre(games: ArrayList<GenreGames>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Popular Games",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(10.dp))

    games.forEach {
        GenreGamesItem(it)
        Spacer(modifier = Modifier.height(5.dp))
    }

}

@Composable
private fun GenreGamesItem(genreGames: GenreGames) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = genreGames.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "${genreGames.added}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = R.drawable.ic_user_unfilled),
                contentDescription = "population icon"
            )
        }
    }
}
