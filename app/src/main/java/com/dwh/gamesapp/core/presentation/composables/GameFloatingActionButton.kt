package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GameFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 15,
                delayMillis = 30,
                easing = LinearEasing,
            ),
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 15,
                delayMillis = 150,
                easing = LinearEasing,
            )
        ),
    ) {
        val fabScale by transition.animateFloat(
            transitionSpec = {
                tween(
                    durationMillis = when (targetState) {
                        EnterExitState.PreEnter,
                        EnterExitState.Visible,
                        -> 330

                        EnterExitState.PostExit -> 135
                    },
                    delayMillis = 0,
                    easing = LinearOutSlowInEasing,
                )
            },
            label = "FAB scale"
        ) {
            when (it) {
                EnterExitState.PreEnter,
                EnterExitState.PostExit,
                -> 0f

                EnterExitState.Visible -> 1f
            }
        }
        SmallFloatingActionButton(
            modifier = Modifier.graphicsLayer {
                scaleX = fabScale
                scaleY = fabScale
            },
            onClick = onClick,
            interactionSource = interactionSource,
            shape = shape,
            containerColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation,
        ) {
            val contentScale by transition.animateFloat(
                transitionSpec = {
                    tween(
                        durationMillis = when (targetState) {
                            EnterExitState.PreEnter,
                            EnterExitState.Visible,
                            -> 240

                            EnterExitState.PostExit -> 135
                        },
                        delayMillis = when (targetState) {
                            EnterExitState.PreEnter,
                            EnterExitState.Visible,
                            -> 90

                            EnterExitState.PostExit -> 0
                        },
                        easing = FastOutLinearInEasing,
                    )
                },
                label = "FAB content scale"
            ) {
                when (it) {
                    EnterExitState.PreEnter,
                    EnterExitState.PostExit,
                    -> 0f

                    EnterExitState.Visible -> 1f
                }
            }
            Box(
                Modifier.graphicsLayer {
                    scaleX = contentScale
                    scaleY = contentScale
                }
            ) {
                content()
            }
        }
    }
}