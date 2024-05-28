package com.dwh.gamesapp.presentation.ui.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
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
import com.dwh.gamesapp.domain.model.game.NextWeekGamesResults
import com.dwh.gamesapp.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.presentation.composables.CustomScaffold
import com.dwh.gamesapp.presentation.composables.EmptyData
import com.dwh.gamesapp.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.utils.GameUiInfo
import com.dwh.gamesapp.utils.LocalGameUiInfo
import com.dwh.gamesapp.utils.vertical
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import com.dwh.gamesapp.presentation.navigation.Screens
import com.dwh.gamesapp.presentation.view_model.UIState
import com.dwh.gamesapp.presentation.view_model.home.HomeViewModel
import java.lang.Math.abs
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(homeViewModel) {
        homeViewModel.getNextWeekGames(getDates())
    }

    CustomScaffold(navController = navController) {
        BackgroundGradient()
        NextWeekGamesValidationResponse(navController, homeViewModel)

    }
}

@Composable
fun NextWeekGamesValidationResponse(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is UIState.Error -> {
            val erroMsg = (uiState as UIState.Error).errorMessage
            Log.e("GameDetailsScreenError", erroMsg)
            EmptyData(
                title = "Sin información disponible",
                description = "No se han encontrado géneros por el momento, inténtelo más tarde"
            )
        }

        UIState.Loading -> LoadingAnimation()

        is UIState.Success -> {
            val nextWeekGames = (uiState as UIState.Success).data
            HomeContent(navController, nextWeekGames)
        }
    }
}

@Composable
private fun HomeContent(
    navController: NavController,
    nextWeekGames: List<NextWeekGamesResults>?
) {
    val list = mutableListOf(
        GameData(painterResource(id = R.drawable.game_cover_image), "Big bang theory"),
        GameData(painterResource(id = R.drawable.game_cover_image), "Star wars"),
        GameData(painterResource(id = R.drawable.game_cover_image), "Vámonooooos"),
        GameData(painterResource(id = R.drawable.game_cover_image), "Ayoooooooooosh cabaaalloooos")
    )

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

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = SpaceEvenly) {
            TopItemsRow("Genres") { navController.navigate(Screens.GENRES_SCREEN) }
            TopItemsRow("Plattforms") { navController.navigate(Screens.PLATFORMS_SCREEN) }
        }

        Spacer(modifier = Modifier.height(10.dp))



        if(nextWeekGames?.isNotEmpty() == true) {
            CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
                NextGamesReleasesHorizontalList(nextWeekGames, navController, "Best of the year")
            }

            Spacer(modifier = Modifier.height(20.dp))

            CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
                NextGamesReleasesHorizontalList(nextWeekGames, navController, "New releases")
            }

            Spacer(modifier = Modifier.height(20.dp))

            CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
                NextGamesReleasesHorizontalList(nextWeekGames = nextWeekGames, navController, "Next releases")
            }
        }
    }
}

@Composable
private fun TopItemsRow(
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
private fun GamesOfTheYearHorizontalList() {

}

@Composable
private fun NewGamesReleseaseHorizontalList() {

}

@OptIn(ExperimentalSnapperApi::class)
@Composable
private fun NextGamesReleasesHorizontalList(
    nextWeekGames: List<NextWeekGamesResults>,
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
        LazyRow (
            state = scrollState,
            flingBehavior = rememberSnapperFlingBehavior(scrollState),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                GamesScrollBuffer(title)
            }
            items(nextWeekGames) { game ->
                GameItem(game, navController)
            }
            item {
                GamesScrollBuffer(title)
            }
        }
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

@Composable
private fun GameItem(
    game: NextWeekGamesResults,
    navController: NavController
) {
    val gameUiInfo = LocalGameUiInfo.current
    var itemX by remember { mutableStateOf(0f) }
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
            /*Image(
                modifier = Modifier.fillMaxSize(),
                painter = game.backgroundImage,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )*/
        }

        Text(
            text = game.name,
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

@RequiresApi(Build.VERSION_CODES.O)
fun getDates(): String {
    val dateNow = LocalDate.now()
    val dateNextWeek = dateNow.plusDays(7)

    return "$dateNow,$dateNextWeek"
}

data class GameData(
    val image: Painter,
    val title: String
)




