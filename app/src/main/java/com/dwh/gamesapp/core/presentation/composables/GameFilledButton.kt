package com.dwh.gamesapp.core.presentation.composables

import android.media.MediaPlayer
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.core.presentation.theme.Dogica
import com.dwh.gamesapp.core.presentation.theme.primary
import com.dwh.gamesapp.core.presentation.theme.secondary
import com.dwh.gamesapp.core.presentation.utils.modifier.ShakeConfig
import com.dwh.gamesapp.core.presentation.utils.modifier.bounceClickEffect
import com.dwh.gamesapp.core.presentation.utils.modifier.rememberShakeController
import com.dwh.gamesapp.core.presentation.utils.modifier.shake
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameFilledButton(
    modifier: Modifier = Modifier,
    nameButton: String,
    enabled: Boolean = true,
    anErrorOccurred: Boolean = false,
    buttonSound: Int = R.raw.sonic_ring,
    onClick: () -> Unit
) {
    val sound = if (!enabled || anErrorOccurred) R.raw.pacman_hurt else buttonSound
    val mediaPlayer = MediaPlayer.create(LocalContext.current, sound)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shakeController = rememberShakeController()
    val animateColor by animateColorAsState(
        targetValue = if (isPressed) primary else secondary,
        label = "",
        animationSpec = tween(150, easing = LinearEasing)
    )
    val backGroundColor = if (enabled) animateColor else MaterialTheme.colorScheme.outline
    val textColor = if (enabled) Color.White else Color.Black

    Box(
        modifier = modifier
            .bounceClickEffect()
            .shake(shakeController)
            .clickable(interactionSource = interactionSource, indication = null) {
                mediaPlayer.start()
                if (enabled) onClick()
                if (anErrorOccurred || !enabled ) {
                    shakeController.shake(
                        ShakeConfig(iterations = 4, intensity = 2_000f, rotateY = 15f, translateX = 40f)
                    )
                }
            }
            .background(
                color = backGroundColor,
                shape = CircleShape
            )
            .padding(vertical = 10.dp, horizontal = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = nameButton,
            style = MaterialTheme.typography.titleSmall.copy(fontFamily = Dogica),
            color = textColor
        )
    }
}