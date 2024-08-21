package com.dwh.gamesapp.games_details.presentation

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.DescriptionComposable
import com.dwh.gamesapp.core.presentation.composables.CoverImageWithBackIconParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.ScrollingTitleComposable
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.theme.Dark_Green
import com.dwh.gamesapp.core.presentation.theme.Light_Green
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games_details.presentation.utils.DateFormatter.formattedDate
import com.dwh.gamesapp.games_details.presentation.utils.generateStringFromList
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener

@Composable
fun GameDetailsScreen(
    navController: NavController,
    gameId: String?,
    viewModel: GameDetailsViewModel
) {
    if(!gameId.isNullOrEmpty()) {
        LaunchedEffect(viewModel) {
            viewModel.getGameDetails(gameId.toInt())
            viewModel.isMyFavoriteGame(gameId.toInt())
        }
    }

    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        GameDetailsContent(navController, viewModel)
    }
}

@Composable
private fun GameDetailsContent(
    navController: NavController,
    viewModel: GameDetailsViewModel
) {
    GameDetailsValidateResponse(
        viewModel,
        navController
    )
}

@Composable
private fun GameDetailsValidateResponse(
    viewModel: GameDetailsViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is DataState.Error -> {
            val errorMsg = (uiState as DataState.Error).errorMessage
            Log.e("ERROR: GameDetailsScreenError", errorMsg)
            InformationCard(
                modifier = Modifier.fillMaxSize(),
                message = "Ocurrió un error",
                description = errorMsg
            )
        }

        DataState.Loading -> LoadingAnimation()

        is DataState.Success -> {
            val gameDetails = (uiState as DataState.Success).data
            GameDetailsContentWithParallaxEffect(navController, gameDetails, viewModel)
        }
    }
}

@Composable
fun GameDetailsContentWithParallaxEffect(
    navController: NavController,
    gameDetails: GameDetails?,
    viewModel: GameDetailsViewModel
) {
    val scrollState = rememberScrollState()

    LifecycleOwnerListener()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background.copy(.8f))
            .statusBarsPadding()
    ) {
        CoverImageWithBackIconParallaxEffect(
            scrollState = scrollState,
            imageUrl = gameDetails?.backgroundImageAdditional ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { navController.popBackStack() }

        ScrollingTitleComposable(
            scrollState = scrollState,
            gameDetails?.nameOriginal ?: "N/A"
        )

        Box(Modifier.padding(top = 60.dp)) {
            GameInformation(scrollState, gameDetails, viewModel)
        }
    }
}

@Composable
private fun GameInformation(
    scrollState: ScrollState,
    gameDetails: GameDetails?,
    viewModel: GameDetailsViewModel
) {
    val context = LocalContext.current
    val isMyFavoriteGame by viewModel.favoriteGame.collectAsStateWithLifecycle()
    var isSelected by remember { mutableStateOf(isMyFavoriteGame) }
    val iconTint = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant

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

        /** TODO: Re hacer la lógica */
        AddFavoritesIcon(viewModel, gameDetails, iconTint) {
            /*if(isSelected) {
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
            }*/
        }

        Spacer(modifier = Modifier.height(15.dp))

        DescriptionComposable(gameDetails?.descriptionRaw, isHeaderDisplayed = true)

        Spacer(modifier = Modifier.height(20.dp))

        GameAttributesGrid(gameDetails)
    }
}

@Composable
private fun DateAndPlatforms(gameDetails: GameDetails?) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DateText(gameDetails)

        Spacer(modifier = Modifier.width(10.dp))

        PlatformsIcons(gameDetails)
    }
}

@Composable
private fun DateText(gameDetails: GameDetails?) {
    Text(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground, RoundedCornerShape(5.dp))
            .padding(5.dp),
        text = formattedDate(gameDetails?.released ?: ""),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.background
    )
}

@Composable
private fun PlatformsIcons(gameDetails: GameDetails?) {
    val platformIcons = arrayListOf<Int>()
    var others = 0
    val parentPlatforms = arrayListOf<String>()

    gameDetails?.parentPlatforms?.forEach { platforms ->
        parentPlatforms.add(platforms.platform?.name ?: "")
    }

    parentPlatforms.forEach { platform ->
        when {
            platform.contains("PC") -> platformIcons.add(R.drawable.ic_windows_logo)
            platform.contains("Xbox") -> platformIcons.add(R.drawable.ic_xbox_logo)
            platform.contains("PlayStation") -> platformIcons.add(R.drawable.ic_play_station_logo)
            platform.contains("Apple Macintosh") -> platformIcons.add(R.drawable.ic_mac_logo)
            platform.contains("Nintendo") -> platformIcons.add(R.drawable.ic_nintendo_logo)
            else -> others += 1
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        platformIcons.forEach {
            Icon(
                modifier = Modifier.size(15.dp),
                painter = painterResource(id = it),
                contentDescription = "platform icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
    if (others != 0)
        Text(
            text = "+$others",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
}

@Composable
private fun AddFavoritesIcon(
    viewModel: GameDetailsViewModel,
    gameDetails: GameDetails?,
    colorTint: Color,
    onClickAction: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable {
                    onClickAction()
                },
            painter = painterResource(id = R.drawable.ic_add_favorite),
            contentDescription = "kirby add to favorites icon",
            tint = colorTint
        )
    }
}

@Composable
private fun GameAttributesGrid(gameDetails: GameDetails?) {
    val platforms = generateStringFromList(gameDetails?.platforms) { it.platform?.name ?: "N/A" }
    val genres = generateStringFromList(gameDetails?.genres) { it.name ?: "N/A" }
    val developers = generateStringFromList(gameDetails?.developers) { it.name ?: "N/A" }
    val publishers = generateStringFromList(gameDetails?.publishers) { it.name ?: "N/A" }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        /** Plataforms and Metascore */
        GameAttributeTitlesRow(
            title1 = "Platforms",
            title2 = "Metascore",
        )
        GameAttributeValuesRow(
            value1 = platforms,
            value2 = "${gameDetails?.metacritic ?: 0}",
            showMetacriticItem = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        /** Genre and Release date */
        GameAttributeTitlesRow(
            title1 = "Genre",
            title2 = "Release date",
        )
        GameAttributeValuesRow(
            value1 = genres,
            value2 = formattedDate(gameDetails?.released ?: "N/A")
        )

        Spacer(modifier = Modifier.height(20.dp))

        /** Developer and Publisher */
        GameAttributeTitlesRow(
            title1 = "Developer",
            title2 = "Publisher",
        )
        GameAttributeValuesRow(
            modifier = Modifier.fillMaxWidth(),
            value1 = developers,
            value2 = publishers
        )

        Spacer(modifier = Modifier.height(20.dp))

        /** Age rating */
        GameAttributeTitlesRow(
            title1 = "Age rating",
            showRightItem = false
        )
        GameAttributeValuesRow(
            value1 = gameDetails?.esrbRatingResponse?.name ?: "N/A",
            showRightItem = false
        )
    }
}

@Composable
private fun GameAttributeTitlesRow(
    title1: String,
    title2: String = "",
    showRightItem: Boolean = true,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GameAttributeText(text = title1)
        if (showRightItem) {
            GameAttributeText(text = title2)
        }
    }
}

@Composable
private fun GameAttributeValuesRow(
    modifier: Modifier = Modifier,
    value1: String,
    value2: String = "",
    showRightItem: Boolean = true,
    showMetacriticItem: Boolean = false,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        GameAttributeText(
            text = value1,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.scrim
        )
        if (showRightItem) {
            if (showMetacriticItem) {
                MetacriticAttributeText(text = value2)
            } else {
                GameAttributeText(
                    modifier = modifier,
                    text = value2,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.scrim
                )
            }
        }
    }
}

@Composable
private fun GameAttributeText(
    modifier: Modifier = Modifier.fillMaxWidth(.45f),
    text: String,
    style: TextStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = color
    )
}

@Composable
private fun MetacriticAttributeText(text: String) {
    val metacriticColor = if (isSystemInDarkTheme()) Dark_Green else Light_Green

    Text(
        modifier = Modifier
            .border(1.dp, metacriticColor, shape = RoundedCornerShape(5.dp))
            .padding(3.dp),
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = metacriticColor
    )
}