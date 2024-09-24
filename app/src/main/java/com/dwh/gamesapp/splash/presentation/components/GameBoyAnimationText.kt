package com.dwh.gamesapp.splash.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwh.gamesapp.core.presentation.theme.GillSans
import com.dwh.gamesapp.core.presentation.theme.name_app
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameBoyAdvanceAnimationText() {
    val text = "GAMES APP"
    val gradientColor = listOf(Color.White, Color.Magenta, name_app)
    val scaleList = remember { List(text.length) { Animatable(0f) } }
    val offsetList = remember { List(text.length) { Animatable(0f) } }
    var gradientVisible by remember { mutableStateOf(false) }
    val animatedOffsetX = remember { Animatable(-100f) }

    LaunchedEffect(Unit) {
        text.forEachIndexed { index, _ ->
            launch {
                delay(100L * index)

                scaleList[index].animateTo(
                    targetValue = 1.8f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
                scaleList[index].animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = FastOutSlowInEasing
                    )
                )

                offsetList[index].animateTo(
                    targetValue = -10f,
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = FastOutSlowInEasing
                    )
                )
                offsetList[index].animateTo(
                    targetValue = 0f,
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }

        delay((100L * text.length) + 1500)

        gradientVisible = true
    }

    LaunchedEffect(gradientVisible) {
        if (gradientVisible) {
            animatedOffsetX.animateTo(
                targetValue = 1100f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = LinearEasing
                )
            )
        }
    }

    val gradient = Brush.radialGradient(
        colors = gradientColor,
        center = Offset(animatedOffsetX.value, 0f),
        radius = 250f
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        if (gradientVisible) {
            Text(
                text = text,
                color = name_app,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = GillSans,
                    brush = gradient,
                    fontSize = 60.sp
                )
            )
        } else {
            text.forEachIndexed { index, char ->
                Text(
                    text = char.toString(),
                    color = name_app,
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = GillSans, fontSize = 60.sp),
                    modifier = Modifier
                        .scale(scaleList[index].value)
                        .offset(y = offsetList[index].value.dp)
                )
            }
        }
    }
}