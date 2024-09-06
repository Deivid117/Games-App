package com.dwh.gamesapp.core.presentation.composables

import android.media.MediaPlayer
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.Dogica
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient
import com.dwh.gamesapp.core.presentation.utils.modifier.ShakeConfig
import com.dwh.gamesapp.core.presentation.utils.modifier.bounceClickEffect
import com.dwh.gamesapp.core.presentation.utils.modifier.rememberShakeController
import com.dwh.gamesapp.core.presentation.utils.modifier.shake

@Composable
fun GameOutlinedButton(
    modifier: Modifier = Modifier,
    nameButton: String,
    anErrorOccurred: Boolean = false,
    buttonSound: Int = R.raw.crash_woah,
    onClick: () -> Unit
) {
    val mediaPlayer = MediaPlayer.create(LocalContext.current, buttonSound)

    val brushGradientColor = Brush.linearGradient(
        0.0f to primary_gradient,
        500.0f to secondary_gradient,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shakeController = rememberShakeController()
    val animateColor by animateColorAsState(
        targetValue = if (isPressed) Color.LightGray else Color.White,
        label = "",
        animationSpec = tween(150, easing = LinearEasing)
    )

    Box(
        modifier = modifier
            .bounceClickEffect()
            .shake(shakeController)
            .clickable(interactionSource = interactionSource, indication = null){
                mediaPlayer.start()
                onClick()
                if(anErrorOccurred) {
                    shakeController.shake(
                        ShakeConfig(iterations = 4, intensity = 2_000f, rotateY = 15f, translateX = 40f)
                    )
                }
            }
            .border(width = 3.dp, brush = brushGradientColor, shape = CircleShape)
            .background(color = animateColor, shape = CircleShape)
            .padding(vertical = 10.dp, horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = nameButton,
            style = MaterialTheme.typography.titleSmall.copy(fontFamily = Dogica),
            color = Color.Black
        )
    }
}