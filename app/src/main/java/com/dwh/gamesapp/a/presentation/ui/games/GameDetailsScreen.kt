package com.dwh.gamesapp.a.presentation.ui.games

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.model.game_details.GameDetails
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.DescriptionComposable
import com.dwh.gamesapp.core.presentation.composables.CoverImageWithBackIconParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.ScrollingTitleComposable
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.a.presentation.composables.TopAppBarComposable
import com.dwh.gamesapp.a.presentation.ui.theme.Dark_Green
import com.dwh.gamesapp.a.presentation.ui.theme.Light_Green
import com.dwh.gamesapp.a.presentation.view_model.game_details.GameDetailsUiState
import com.dwh.gamesapp.a.presentation.view_model.game_details.GameDetailsViewModel
import com.dwh.gamesapp.utils.Constants.headerHeight
import com.dwh.gamesapp.utils.Constants.toolbarHeight
import com.dwh.gamesapp.utils.LifecycleOwnerListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashSet

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameDetailsScreen(
    navController: NavController,
    gameId: Int,
    viewModel: GameDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getGameDetails(gameId)
        viewModel.isMyFavoriteGame(gameId)
    }

    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        DetailsValidationResponse(viewModel, navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DetailsValidationResponse(
    viewModel: GameDetailsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is GameDetailsUiState.Error -> {
            val erroMsg = (uiState as GameDetailsUiState.Error).errorMessage
            Log.e("GameDetailsScreenError", erroMsg)
            EmptyData(
                title = "Sin información disponible",
                description = "No se ha encontrado información del juego por el momento, inténtelo más tarde"
            )
        }
        GameDetailsUiState.Loading -> {
            LoadingAnimation()
        }
        is GameDetailsUiState.Success -> {
            val data = (uiState as GameDetailsUiState.Success).data
            GameDetailsContent(navController, data, viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun GameDetailsContent(
    navController: NavController,
    gameDetails: GameDetails,
    viewModel: GameDetailsViewModel
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
            imageUrl = gameDetails.backgroundImageAdditional,
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { navController.popBackStack() }

        Box(Modifier.padding(top = 60.dp)) {
            GameBody(scrollState = scrollState, gameDetails, viewModel)
        }

        TopAppBarComposable(
            scrollState = scrollState,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
        ) { navController.popBackStack() }

        ScrollingTitleComposable(scrollState = scrollState, gameDetails.nameOriginal)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun GameBody(
    scrollState: ScrollState,
    gameDetails: GameDetails,
    viewModel: GameDetailsViewModel
) {
    val metacriticColor = if(isSystemInDarkTheme()) Dark_Green else Light_Green
    val context = LocalContext.current
    val isMyFavoriteGame by viewModel.favoriteGame.collectAsStateWithLifecycle()
    var isSelected by remember { mutableStateOf(isMyFavoriteGame) }
    val iconTint = if(isSelected) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(headerHeight - 50.dp))

        DateAndPlatforms(gameDetails)
        
        Spacer(modifier = Modifier.height(15.dp))

        AddFavoriteIcon(viewModel, gameDetails, iconTint) {
            if(isSelected) {
                viewModel.removerFavoriteGame(gameDetails.id) {success ->
                    if(success) {
                        isSelected = !isSelected
                        Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                viewModel.addFavoriteGame(
                    FavoritGame(
                        id = gameDetails.id,
                        name = gameDetails.nameOriginal,
                        released = gameDetails.released,
                        background_image = gameDetails.backgroundImage,
                        metacritic = gameDetails.metacritic
                    )
                ) {
                    if (it) {
                        isSelected = !isSelected
                        Toast.makeText(context, "Agregado a favoritos", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        DescriptionComposable(gameDetails.descriptionRaw, isHeaderDisplayed = true)

        Spacer(modifier = Modifier.height(20.dp))

        GameDetailsVerticalGrid(metacriticColor, gameDetails)
    }
}

@Composable
private fun AddFavoriteIcon(
    viewModel: GameDetailsViewModel,
    gameDetails: GameDetails,
    colorTint: Color,
    onClickAction: () -> Unit
) {


    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable {
                    onClickAction()
                },
            painter = painterResource(id = R.drawable.ic_add_favorite),
            contentDescription = "kirby add favorite icon",
            tint = colorTint
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateAndPlatforms(gameDetails: GameDetails) {
    val platform = arrayListOf<String>()
    gameDetails.platforms.forEach {
        platform.add(it.platform.name)
    }
    val platformIcons = arrayListOf<Int>()
    var others = 0
    platform.forEach {
        when {
            it.contains("PC") -> { platformIcons.add(R.drawable.ic_windows_logo) }
            it.contains("Xbox") -> { platformIcons.add(R.drawable.ic_xbox_logo) }
            it.contains("PlayStation") -> { platformIcons.add(R.drawable.ic_play_station_logo) }
            it.contains("macOS") -> { platformIcons.add(R.drawable.ic_mac_logo) }
            it.contains("Nintendo") -> { platformIcons.add(R.drawable.ic_nintendo_logo) }
            else -> { others += 1 }
        }
    }
    val nonDuplicatedPlatformIcons = HashSet<Int>()
    nonDuplicatedPlatformIcons.addAll(platformIcons)

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(5.dp))
                .padding(5.dp),
            text = dateFormat(gameDetails.released),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.background
        )

        Spacer(modifier = Modifier.width(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            nonDuplicatedPlatformIcons.forEach {
                Icon(
                    modifier = Modifier.size(15.dp),
                    painter = painterResource(id = it),
                    contentDescription = "plattform icon",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            if(others != 0) {
                Text(text = "+$others", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun GameDetailsVerticalGrid(
    metacriticColor: Color,
    gameDetails: GameDetails
) {
    var plattforms = ""
    for ((index, i) in gameDetails.platforms.withIndex()) {
        plattforms += if(index < gameDetails.platforms.size - 1) "${i.platform.name}, " else i.platform.name
    }

    var genres = ""
    for ((index, i) in gameDetails.genres.withIndex()) {
        genres += if(index < gameDetails.genres.size - 1) "${i.name}, " else i.name
    }

    var developers = ""
    for ((index, i) in gameDetails.developers.withIndex()) {
        developers += if(index < gameDetails.developers.size - 1) "${i.name}, " else i.name
    }

    var publishers = ""
    for ((index, i) in gameDetails.publishers.withIndex()) {
        publishers += if(index < gameDetails.genres.size - 1) "${i.name}, " else i.name
    }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        maxItemsInEachRow = 2
    ) {
        DetailHeaderGrid("Plattforms", "Metascore")
        DetailBodyRatingGrid(
            metacriticColor,
            plattforms,
            gameDetails.metacritic.toString()
        )

        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))

        DetailHeaderGrid("Genre", "Release date")
        DetailBodyGrid(genres, dateFormat(gameDetails.released))

        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))

        DetailHeaderGrid("Developer", "Publisher")
        DetailBodyGrid(developers, publishers)

        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))

        DetailHeaderGrid("Age rating", "")
        BodyItemGrid(text = gameDetails.esrbRatingResponse?.name ?: "N/A")
    }
}

@Composable
private fun DetailHeaderGrid(title1: String, title2: String) {
    HeaderItemGrid(title1)
    HeaderItemGrid(title2)
}

@Composable
private fun HeaderItemGrid(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth(.45f),
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun DetailBodyGrid(body1: String, body2: String) {
    BodyItemGrid(body1)
    BodyItemGrid(body2)
}

@Composable
private fun DetailBodyRatingGrid(metacriticColor: Color, body1: String, body2: String) {
    BodyItemGrid(body1)
    Column(modifier = Modifier.fillMaxWidth(.45f)) {
        Text(
            modifier = Modifier
                .border(1.dp, metacriticColor, shape = RoundedCornerShape(5.dp))
                .padding(3.dp),
            text = body2,
            style = MaterialTheme.typography.bodyMedium,
            color = metacriticColor
        )
    }
}

@Composable
private fun BodyItemGrid(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth(.45f),
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.scrim
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormat(date: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)

    val dateFormated = LocalDate.parse(date, inputFormatter)
    return outputFormatter.format(dateFormated)
}