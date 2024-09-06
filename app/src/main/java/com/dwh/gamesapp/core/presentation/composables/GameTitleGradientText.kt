package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwh.gamesapp.core.presentation.theme.Dogica
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient
import com.dwh.gamesapp.core.presentation.theme.tertiary_gradient
import com.dwh.gamesapp.core.presentation.theme.white_gradient

@Composable
fun GameTitleGradientText(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 22.sp
) {
    val currentFontSizePx = with(LocalDensity.current) { (22.dp).toPx() }
    val currentFontSizeDoublePx = currentFontSizePx * 2

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = currentFontSizeDoublePx,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = ""
    )

    val gradientColor = listOf(
        primary_gradient,
        tertiary_gradient,
        secondary_gradient,
        white_gradient
    )

    val brush = Brush.linearGradient(
        colors = gradientColor,
        start = Offset(offset, offset),
        end = Offset(offset + currentFontSizePx, offset + currentFontSizePx),
        tileMode = TileMode.Mirror
    )

    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontFamily = Dogica,
            brush = brush,
            fontWeight = fontWeight,
            fontSize = fontSize
        ),
        textAlign = TextAlign.Center
    )
}