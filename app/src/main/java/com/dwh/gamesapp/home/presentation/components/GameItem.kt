package com.dwh.gamesapp.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.home.domain.model.GameItem
import com.dwh.gamesapp.home.presentation.utils.LocalGameUiInfo
import kotlin.math.abs

@Composable
fun GameItem(
    game: GameItem,
    navigateToGameDetails: (Int) -> Unit
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
                .clickable { navigateToGameDetails(game.id ?: 0) },
            shape = RectangleShape
        ) {
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
                .alpha(alpha = alpha)
        )
    }
}