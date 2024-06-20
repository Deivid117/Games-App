package com.dwh.gamesapp.home.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceEvenly
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.a.presentation.composables.CustomScaffold
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.utils.GameUiInfo
import com.dwh.gamesapp.utils.LocalGameUiInfo
import com.dwh.gamesapp.utils.vertical
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import com.dwh.gamesapp.a.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.home.domain.model.BestOfTheYearResults
import com.dwh.gamesapp.home.domain.model.GameItem
import com.dwh.gamesapp.home.domain.model.NextWeekGamesResults
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(homeViewModel) {
        homeViewModel.getNextWeekGames()
        homeViewModel.getBestOfTheYear()
    }

    CustomScaffold(navController = navController) {
        BackgroundGradient()
        HomeContent(navController, homeViewModel)
    }
}

@Composable
private fun HomeContent(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val gameUiInfo = GameUiInfo.create(
        screenWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
        itemWidthDp = 170f,
        parallaxOffsetFactor = .33f,
        itemWidthFactorForFadeDistance = .5f,
    )

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp)
    ) {
        GenresPlatformsButtons(navController)

        Spacer(modifier = Modifier.height(10.dp))

        BestOfTheYearValidationResponse(navController, homeViewModel, gameUiInfo)

        Spacer(modifier = Modifier.height(20.dp))

        NextWeekGamesValidationResponse(navController, homeViewModel, gameUiInfo)

        Spacer(modifier = Modifier.height(20.dp))

        //NewGamesReleasesContent()
    }
}

@Composable
private fun GenresPlatformsButtons(navController: NavController) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = SpaceEvenly
    ) {
        ItemButton("Genres") { navController.navigate(Screens.GENRES_SCREEN) }
        ItemButton("Platforms") { navController.navigate(Screens.PLATFORMS_SCREEN) }
    }
}

@Composable
private fun ItemButton(
    title: String,
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .clickable { onClick() }
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(5.dp))
            .padding(vertical = 5.dp, horizontal = 8.dp),
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun BestOfTheYearValidationResponse(
    navController: NavController,
    homeViewModel: HomeViewModel,
    gameUiInfo: GameUiInfo
) {
    val uiState by homeViewModel.uiStateBOTY.collectAsStateWithLifecycle()

    when (uiState) {
        is UIState.Error -> {
            val errorMsg = (uiState as UIState.Error).errorMessage
            Log.e("ERROR: HomeScreenError", errorMsg)
            EmptyData(
                title = "Ocurrió un error",
                description = errorMsg
            )
        }

        UIState.Loading -> LoadingAnimation()

        is UIState.Success -> {
            val bestOfTheYearResults = (uiState as UIState.Success).data
            BestOfTheYearContent(navController, bestOfTheYearResults, gameUiInfo)
        }
    }
}

@Composable
fun NextWeekGamesValidationResponse(
    navController: NavController,
    homeViewModel: HomeViewModel,
    gameUiInfo: GameUiInfo
) {
    val uiState by homeViewModel.uiStateNWG.collectAsStateWithLifecycle()

    when (uiState) {
        is UIState.Error -> {
            val errorMsg = (uiState as UIState.Error).errorMessage
            Log.e("ERROR: HomeScreenError", errorMsg)
            EmptyData(
                title = "Ocurrió un error",
                description = errorMsg
            )
        }

        UIState.Loading -> LoadingAnimation()

        is UIState.Success -> {
            val nextWeekGamesResults = (uiState as UIState.Success).data
            NextWeekGamesContent(navController, nextWeekGamesResults, gameUiInfo)
        }
    }
}

@Composable
fun NewGamesReleasesValidationResponse(){}

@Composable
fun BestOfTheYearContent(
    navController: NavController,
    bestOfTheYearResults: BestOfTheYearResults?,
    gameUiInfo: GameUiInfo
) {
    val bestOfTheYearGames = bestOfTheYearResults?.results ?: arrayListOf()

    if (bestOfTheYearGames.isNotEmpty()) {
        CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
            BestOfTheYearHorizontalList(bestOfTheYearGames, navController)
        }
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No hay juegos por mostrar"
        )
    }
}

@Composable
fun NextWeekGamesContent(
    navController: NavController,
    nextWeekGamesResults: NextWeekGamesResults?,
    gameUiInfo: GameUiInfo
) {
    val nextWeekGames = nextWeekGamesResults?.results ?: arrayListOf()

    if (nextWeekGames.isNotEmpty()) {
        CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
            NextWeekGamesHorizontalList(nextWeekGames, navController)
        }
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No hay juegos por mostrar"
        )
    }
}

@Composable
fun NewGamesReleasesContent(){}

@Composable
private fun BestOfTheYearHorizontalList(
    bestOfTheYearGames: List<GameItem>,
    navController: NavController,
) {
    HorizontalListGames(bestOfTheYearGames, navController, "Best of the year")
}

@Composable
private fun NextWeekGamesHorizontalList(
    nextWeekGames: List<GameItem>,
    navController: NavController,
) {
    HorizontalListGames(nextWeekGames, navController, "Next releases")
}

@Composable
private fun NewGamesReleaseHorizontalList() {}

@OptIn(ExperimentalSnapperApi::class)
@Composable
private fun HorizontalListGames(
    games: List<GameItem>,
    navController: NavController,
    title: String
) {
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background.copy(0.5f))
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(
            state = scrollState,
            flingBehavior = rememberSnapperFlingBehavior(scrollState),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                GamesScrollBuffer(title)
            }
            items(games) { game ->
                GameItem(game, navController)
            }
            item {
                GamesScrollBuffer(title)
            }
        }
    }
}

@Composable
private fun GameItem(
    game: GameItem,
    navController: NavController
) {
    val gameUiInfo = LocalGameUiInfo.current
    var itemX by remember { mutableFloatStateOf(0f) }
    val offsetFromCenterPx = itemX - gameUiInfo.xForCenteredItemPx
    val alpha = ((gameUiInfo.parallaxOffsetFadeDistancePx -
            abs(offsetFromCenterPx)) /
            gameUiInfo.parallaxOffsetFadeDistancePx).coerceAtLeast(0f)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            Modifier
                .height(200.dp)
                .width(150.dp)
                .onGloballyPositioned { itemX = it.positionInWindow().x }
                .clickable { navController.navigate(Screens.GAME_DETAILS_SCREEN + "/" + game.id) },
            shape = RectangleShape
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.backgroundImage)
                    .build(),
                contentDescription = "game cover",
                placeholder = painterResource(id = R.drawable.image_controller),
                error = painterResource(id = R.drawable.image_unavailable),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = game.name ?: "N/A",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .width(150.dp)
                .offset {
                    IntOffset(
                        x = (offsetFromCenterPx
                                * gameUiInfo.parallaxOffsetFactor).toInt(), y = 0
                    )
                }
                .alpha(alpha = alpha),
        )
    }
}

@Composable
fun GamesScrollBuffer(title: String) {
    Card(
        Modifier
            .height(220.dp)
            .width(90f.dp),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .vertical()
                    .rotate(-90f),
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                color = Color.White,
                maxLines = 2
            )
        }
    }
}