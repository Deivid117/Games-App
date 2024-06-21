package com.dwh.gamesapp.a.presentation.ui.favorite_games

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.a.presentation.composables.CustomScaffold
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.ui.theme.Dark_Green
import com.dwh.gamesapp.a.presentation.ui.theme.Light_Green
import com.dwh.gamesapp.a.presentation.navigation.Screens
import com.dwh.gamesapp.a.presentation.view_model.favorite_games.FavoriteGamesUiState
import com.dwh.gamesapp.a.presentation.view_model.favorite_games.FavoriteGamesViewModel

@Composable
fun FavoriteGamesScreen(
    navController: NavController,
    viewModel: FavoriteGamesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.getAllFavoriteGames()
    }

    CustomScaffold(navController) {
        BackgroundGradient()
        ValidationFavoriteGamesResponse(viewModel, navController)
    }
}

@Composable
fun ValidationFavoriteGamesResponse(
    viewModel: FavoriteGamesViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is FavoriteGamesUiState.Error -> {
            val erroMsg = (uiState as FavoriteGamesUiState.Error).errorMessage
            Log.e("GameDetailsScreenError", erroMsg)
        }
        is FavoriteGamesUiState.Success -> {
            val data = (uiState as FavoriteGamesUiState.Success).data
            FavoriteGamesContent(data, navController)
        }
    }
}

@Composable
fun FavoriteGamesContent(games: List<FavoritGame>, navController: NavController) {
    if(games.isNotEmpty()) {
        FavoriteGamesList(games, navController)
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No se han encontrado juegos por el momento, inténtelo más tarde"
        )
    }
}

@Composable
fun FavoriteGamesList(games: List<FavoritGame>, navController: NavController) {
    val metacriticColor = if(isSystemInDarkTheme()) Light_Green else Dark_Green

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ){
        itemsIndexed(games) {index, item ->
            FavoriteGameItem(gamesResults = item, metacriticColor) {
                navController.navigate(Screens.GAME_DETAILS_SCREEN + "/" + item.id)
            }
        }
    }
}

@Composable
private fun FavoriteGameItem(
    gamesResults: FavoritGame,
    metacriticColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        Column() {
            AsyncImage(
                modifier = Modifier.background(Color.LightGray).fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gamesResults.background_image)
                    .build(),
                contentDescription = "game cover",
                placeholder = painterResource(id = R.drawable.image_controller_placeholder),
                error = painterResource(id = R.drawable.image_unavailable_error),
                contentScale = ContentScale.Crop,
            )
            Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = gamesResults.released,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        modifier = Modifier
                            .border(1.dp, metacriticColor, shape = RoundedCornerShape(5.dp))
                            .padding(3.dp),
                        text = "${gamesResults.metacritic}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = metacriticColor
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = gamesResults.name, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}