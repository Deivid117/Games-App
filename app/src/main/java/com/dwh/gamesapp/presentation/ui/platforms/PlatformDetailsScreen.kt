package com.dwh.gamesapp.presentation.ui.platforms

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
import com.dwh.gamesapp.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.domain.model.plattform.PlattformGames
import com.dwh.gamesapp.presentation.composables.*
import com.dwh.gamesapp.presentation.view_model.platform_details.PlatformDetailsUiState
import com.dwh.gamesapp.presentation.view_model.platform_details.PlatformDetailsViewModel
import com.dwh.gamesapp.utils.Constants.headerHeight
import com.dwh.gamesapp.utils.Constants.toolbarHeight

@Composable
fun PlatformDetailsScreen(
    navController: NavController,
    platformId: Int,
    games: ArrayList<PlattformGames>,
    viewModel: PlatformDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getPlatformDetails(platformId)
    }

    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        PlatformDetailsValidateResponse(viewModel, navController, games)
    }

}

@Composable
private fun PlatformDetailsValidateResponse(
    viewModel: PlatformDetailsViewModel,
    navController: NavController,
    games: ArrayList<PlattformGames>
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is PlatformDetailsUiState.Error -> {
            val erroMsg = (uiState as PlatformDetailsUiState.Error).errorMessage
            Log.e("GameDetailsScreenError", erroMsg)
            EmptyData(
                title = "Sin información disponible",
                description = "No se han encontrado detalles de la plataforma por el momento, inténtelo más tarde"
            )
        }
        PlatformDetailsUiState.Loading -> {
            LoadingAnimation()
        }
        is PlatformDetailsUiState.Success -> {
            val data = (uiState as PlatformDetailsUiState.Success).data
            PlatformDetailsContent(navController, data, games)
        }
    }
}

@Composable
private fun PlatformDetailsContent(
    navController: NavController,
    genreDetails: PlatformDetails,
    games: ArrayList<PlattformGames>
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
            PlatformBody(scrollState, genreDetails, games)
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
private fun PlatformBody(
    scrollState: ScrollState,
    genreDetails: PlatformDetails,
    games: ArrayList<PlattformGames>
) {
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

        PopularGamesPlatform(games)
    }
}

@Composable
private fun PopularGamesPlatform(games: ArrayList<PlattformGames>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Popular Games",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(10.dp))

    games.forEach {
        PlatformGamesItem(it)
        Spacer(modifier = Modifier.height(5.dp))
    }

}

@Composable
private fun PlatformGamesItem(plattformGames: PlattformGames) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = plattformGames.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "${plattformGames.added}",
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
