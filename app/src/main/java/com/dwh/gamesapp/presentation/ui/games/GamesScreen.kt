package com.dwh.gamesapp.presentation.ui.games

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.disk.DiskCache
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.domain.model.game.GamesResults
import com.dwh.gamesapp.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.presentation.composables.CustomScaffold
import com.dwh.gamesapp.presentation.composables.EmptyData
import com.dwh.gamesapp.presentation.composables.ShimmerLoadingAnimation
import com.dwh.gamesapp.presentation.ui.theme.Dark_Green
import com.dwh.gamesapp.presentation.ui.theme.Light_Green
import com.dwh.gamesapp.presentation.view_model.games.GamesViewModel
import com.dwh.gamesapp.presentation.navigation.Screens
import java.io.File

@Composable
fun GamesScreen(
    navController: NavController,
    viewModel: GamesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.getAllGamesPaged()
    }

    CustomScaffold(navController) {
        BackgroundGradient()
        ValidationResponse(viewModel, navController)
    }
}

@Composable
private fun ValidationResponse(
    viewModel: GamesViewModel,
    navController: NavController
) {
    val gamesResults = viewModel.gamesState.collectAsLazyPagingItems()

    if (gamesResults.loadState.refresh is LoadState.Loading) {
        /*LoadingAnimation()*/ ShimmerLazyVerticalGrid()
    } else {
        GamesContent(gamesResults, navController)
    }
}

@Composable
fun GamesContent(gamesResults: LazyPagingItems<GamesResults>, navController: NavController) {
    if(gamesResults.itemSnapshotList.isNotEmpty()) {
        GamesList(gamesResults, navController)
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No se han encontrado juegos por el momento, inténtelo más tarde"
        )
    }
}

@Composable
private fun GamesList(gamesResults: LazyPagingItems<GamesResults>, navController: NavController) {
    val context = LocalContext.current
    val metacriticColor = if(isSystemInDarkTheme()) Light_Green else Dark_Green

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ){
        itemsIndexed(gamesResults.itemSnapshotList) {index, item ->
            if (item != null) {
                GameItem(gamesResults = item, index, metacriticColor) {
                    navController.navigate(Screens.GAME_DETAILS_SCREEN + "/" + item.id)
                }
            }
        }
        gamesResults.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem(metacriticColor)
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    Log.e(
                        "LoadStateError",
                        (gamesResults.loadState.refresh as LoadState.Error).error.message.toString()
                    )
                    Toast.makeText(
                        context,
                        (gamesResults.loadState.refresh as LoadState.Error).error.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                loadState.append is LoadState.Error -> {
                    // Muestra un mensaje de error en la parte inferior de la lista
                    item {
                        ErrorItem((gamesResults.loadState.append as LoadState.Error).error.message.toString())
                    }
                }
            }
        }
    }
}

@Composable
private fun GameItem(
    gamesResults: GamesResults,
    index: Int,
    metacriticColor: Color,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        Column() {
            AsyncImage(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gamesResults.backgroundImage)
                    .build(),
                contentDescription = "game cover",
                placeholder = painterResource(id = R.drawable.image_controller),
                error = painterResource(id = R.drawable.image_unavailable),
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

@Composable
private fun LoadingItem(
    color: Color
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = color)
    }
}

@Composable
private fun ErrorItem(message: String) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(Color.Red),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(3.dp))
                    .padding(5.dp)
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White, CircleShape)
                        .size(42.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "error_icon",
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Text(
                    color = Color.White,
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .align(CenterVertically)
                )
            }
        }
    }
}

@Composable
private fun ShimmerLazyVerticalGrid() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ){
        items(count = 10) {

            val heightRandom = (80..150).random()

            ShimmerItem(heightRandom)
        }
    }
}

@Composable
private fun ShimmerItem(heightRandom: Int) {
    Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(
                .8f
            )
        )) {
        Column() {
            ShimmerLoadingAnimation(modifier = Modifier
                .fillMaxSize()
                .height(heightRandom.dp))

            Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {

                ShimmerLoadingAnimation(modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp))

                Spacer(modifier = Modifier.height(10.dp))

                ShimmerLoadingAnimation(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp))
            }
        }
    }
}