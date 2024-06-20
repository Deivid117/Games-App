package com.dwh.gamesapp.a.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R

@Composable
fun VerticalGridItemComposable(
    onClick: () -> Unit,
    imageBackground: String,
    name: String,
    gamesCount: Int
) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
            .size(200.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AsyncImage(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
                    .drawWithCache {
                        val verticalGradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 3,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(verticalGradient, blendMode = BlendMode.Multiply)
                        }
                    },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageBackground)
                    .build(),
                contentDescription = "game cover",
                placeholder = painterResource(id = R.drawable.image_controller),
                error = painterResource(id = R.drawable.image_unavailable),
                contentScale = ContentScale.Crop,
            )
            Column(
                Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    strokeWidth = 2f
                )

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                VerticalGridDetailsPopularGames(gameCount = gamesCount.toString())
            }
        }
    }
}

@Composable
private fun VerticalGridDetailsPopularGames(
    gameCount: String
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedText(text = "Popular items")
        OutlinedText(text = gameCount)
    }
}

@Composable
private fun OutlinedText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    strokeWidth: Float = 1f,
    textAlign: TextAlign = TextAlign.Left,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Box() {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            textAlign = textAlign,
            textDecoration = textDecoration,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = modifier,
            text = text,
            textAlign = textAlign,
            color = MaterialTheme.colorScheme.background,
            style = style.copy(
                drawStyle = Stroke(
                    miter = 10f,
                    width = strokeWidth,
                    join = StrokeJoin.Round
                )
            )
        )
    }
}
