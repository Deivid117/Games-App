package com.dwh.gamesapp.splash.presentation.components

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimationSplashContent(
    scaleAnimation: Animatable<Float, AnimationVector1D>,
    rotateAnimation: Animatable<Float, AnimationVector1D>,
    durationMillisAnimation: Int,
    isUserLoggedIn: Boolean,
    navigateTo: () -> Unit
) {
    LaunchedEffect(isUserLoggedIn) {
        launch {
            scaleAnimation.animateTo(
                targetValue = 0.5F,
                animationSpec = tween(
                    durationMillis = durationMillisAnimation,
                    easing = {
                        OvershootInterpolator(3F).getInterpolation(it)
                    }
                )
            )
        }
        launch {
            rotateAnimation.animateTo(
                targetValue = 360F,
                animationSpec = tween(
                    durationMillis = durationMillisAnimation,
                    easing = {
                        OvershootInterpolator(3F).getInterpolation(it)
                    }
                )
            )
        }

        delay(timeMillis = 3500)

        navigateTo()
    }
}