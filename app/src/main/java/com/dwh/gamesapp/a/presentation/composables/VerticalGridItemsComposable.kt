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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R

@Composable
fun VerticalGridItem(
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
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 3,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
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
                Box() {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = name,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = name,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleLarge.copy(
                            drawStyle = Stroke(
                                miter = 10f,
                                width = 2f,
                                join = StrokeJoin.Round
                            )
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                VerticalGridDetailsItem(title = "Popular items", data = gamesCount.toString())

            }
        }
    }
}

@Composable
private fun VerticalGridDetailsItem(
    title: String,
    data: String
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextBox(title)
        TextBox(data)
    }
}

@Composable
private fun TextBox(text: String) {
    Box() {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.labelLarge.copy(
                drawStyle = Stroke(
                    miter = 10f,
                    width = 1f,
                    join = StrokeJoin.Round
                )
            )
        )
    }
}
