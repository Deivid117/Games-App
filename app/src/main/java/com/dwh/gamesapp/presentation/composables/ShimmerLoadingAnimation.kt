package com.dwh.gamesapp.presentation.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

@Composable
fun ShimmerLoadingAnimation(
    modifier: Modifier,
    widthOfShadowBrush: Int = 700,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
) {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 1.0f),
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
    )

    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )

    Box(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(brush)
        )
    }

}