package com.dwh.gamesapp.games.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.a.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.a.presentation.composables.ShimmerLoadingAnimation
import com.dwh.gamesapp.core.presentation.theme.Light_Green
import com.dwh.gamesapp.core.presentation.theme.Dark_Green
import com.dwh.gamesapp.games.presentation.components.GameButtonToScrollToTop
import com.dwh.gamesapp.games.presentation.utils.CustomException

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    state: GameState,
    games: LazyPagingItems<Game>,
    navigateToGameDetails: (Int) -> Unit
) {
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = state.isRefreshing,
            onRefresh = { viewModel.refreshList { games.refresh() } })
    val listState = rememberLazyStaggeredGridState()

    GameScaffold(
        navController = navController,
        modifier = Modifier.pullRefresh(pullRefreshState),
        isSnackBarVisible = state.isSnackBarVisible,
        snackBarMessage = state.snackBarMessage,
        floatingActionButton = {
            GameButtonToScrollToTop(
                listState = listState,
                listIsNotEmpty = games.itemCount > 0
            )
        },
        onDismissSnackBar = { viewModel.hideSnackBar() }
    ) {
        GameView(
            listState = listState,
            games = games,
            onShowSnackBar = { viewModel.showSnackBar(it) },
            navigateToGameDetails = navigateToGameDetails
        )

        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = state.isRefreshing,
            state = pullRefreshState
        )
    }
}

@Composable
private fun GameView(
    listState: LazyStaggeredGridState,
    games: LazyPagingItems<Game>,
    onShowSnackBar: (String) -> Unit,
    navigateToGameDetails: (Int) -> Unit,
) {
    VerticalStaggeredGridGames(
        listState = listState,
        games = games,
        onShowSnackBar = onShowSnackBar,
        navigateToGameDetails = navigateToGameDetails
    )
}

@Composable
private fun VerticalStaggeredGridGames(
    listState: LazyStaggeredGridState,
    games: LazyPagingItems<Game>,
    onShowSnackBar: (String) -> Unit,
    navigateToGameDetails: (Int) -> Unit,
) {
    val metaCriticColor = if (isSystemInDarkTheme()) Light_Green else Dark_Green
    val loadState = games.loadState
    //val loadState = games.loadState.mediator

    val error = when {
        loadState.append is LoadState.Error -> (loadState.append as? LoadState.Error)?.error
        loadState.refresh is LoadState.Error -> (loadState.refresh as? LoadState.Error)?.error
        else -> null
    }

    if (games.itemSnapshotList.isEmpty()) {
        error?.let {
            if (it is CustomException) {
                InformationCard(
                    modifier = Modifier.fillMaxSize(),
                    message = it.errorMessage.errorMessage,
                    description = it.errorMessage.errorDescription
                )
            }
        }
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalItemSpacing = 8.dp
    ) {
        if(loadState.refresh is LoadState.Loading) {
            items(count = 10) {
                val heightRandom = (80..150).random()
                ShimmerItem(heightRandom)
            }
        } else {
            items(
                count = games.itemCount,
                key = games.itemKey { it.id ?: 0 }
            ) { index ->
                games[index]?.let { game ->
                    GameItem(game = game, metaCriticColor = metaCriticColor) {
                        navigateToGameDetails(game.id ?: 0)
                    }
                }
            }

            games.apply {
                item(span = StaggeredGridItemSpan.FullLine) {
                    if (loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation(size = 200)
                        }
                    }
                }
            }
        }
    }

    if (games.itemSnapshotList.isNotEmpty()) {
        error?.let {
            if (it is CustomException) {
                LaunchedEffect(Unit) {
                    onShowSnackBar((it.errorMessage.errorDescription))
                }
            }
        }
    }

    /*when {
        loadState.refresh is LoadState.Loading -> ShimmerLazyVerticalGrid()
        games.itemSnapshotList.isEmpty() -> {
            val error = when {
                loadState.append is LoadState.Error -> (loadState.append as? LoadState.Error)?.error
                loadState.refresh is LoadState.Error -> (loadState.refresh as? LoadState.Error)?.error
                else -> null
            }

            error?.let {
                if (it is CustomException) {
                    InformationCard(
                        modifier = Modifier.fillMaxSize(),
                        message = it.errorMessage.errorMessage,
                        description = it.errorMessage.errorDescription
                    )
                }
            }
        }

        else -> {
            val error = when {
                loadState.append is LoadState.Error -> (loadState.append as? LoadState.Error)?.error
                loadState.refresh is LoadState.Error -> (loadState.refresh as? LoadState.Error)?.error
                else -> null
            }

            error?.let {
                if (it is CustomException) {
                    LaunchedEffect(Unit) {
                        onShowSnackBar((it.errorMessage.errorDescription))
                    }
                }
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(all = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                verticalItemSpacing = 8.dp
            ) {
                *//*if(loadState.refresh is LoadState.Loading) {
                    items(count = 10) {
                        val heightRandom = (80..150).random()
                        ShimmerItem(heightRandom)
                    }
                } else {*//*
                items(
                    count = games.itemCount,
                    key = games.itemKey { it.id ?: 0 }
                ) { index ->
                    games[index]?.let { game ->
                        GameItem(game = game, metaCriticColor = metaCriticColor) {
                            navigateToGameDetails(game.id ?: 0)
                        }
                    }
                }

                games.apply {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        if (loadState.append is LoadState.Loading) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                LoadingAnimation(size = 200)
                            }
                        }
                    }
                }
                //}
            }
        }
    }*/
}

@Composable
private fun GameItem(
    game: Game,
    metaCriticColor: Color,
    navigateToGameDetails: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { navigateToGameDetails() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.backgroundImage)
                    .crossfade(500)
                    .build(),
                contentDescription = "game cover",
                placeholder = painterResource(id = R.drawable.image_controller_placeholder),
                error = painterResource(id = R.drawable.image_unavailable_error),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = game.released ?: "N/A",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        modifier = Modifier
                            .border(width = 1.dp, color = metaCriticColor, shape = RoundedCornerShape(5.dp))
                            .padding(3.dp),
                        text = "${game.metacritic ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = metaCriticColor
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = game.name ?: "N/A", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun ShimmerItem(heightRandom: Int) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(.8f))) {
        Column {
            /*Box(
                modifier = Modifier.fillMaxSize().height(heightRandom.dp).shimmerAnimation()
            )*/
            ShimmerLoadingAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .height(heightRandom.dp)
            )

            Column(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {

                ShimmerLoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                ShimmerLoadingAnimation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
            }
        }
    }
}