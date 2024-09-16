package com.dwh.gamesapp.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.OutlinedText
import com.dwh.gamesapp.core.presentation.utils.modifier.bounceClickEffect

@Composable
fun HomeItemCard(
    modifier: Modifier = Modifier,
    name: String,
    imageBackground: Int = R.drawable.game_cover_image,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .size(150.dp)
            .bounceClickEffect()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
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
                            drawRect(brush = verticalGradient, blendMode = BlendMode.Multiply)
                        }
                    },
                painter = painterResource(id = imageBackground),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(0.4f))
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedText(
                        modifier = Modifier.fillMaxWidth(),
                        text = name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        textAlign = TextAlign.Center,
                        strokeWidth = 4f
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    thickness = 3.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}