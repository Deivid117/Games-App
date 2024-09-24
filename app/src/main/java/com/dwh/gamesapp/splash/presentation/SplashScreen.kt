package com.dwh.gamesapp.splash.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.splash.presentation.components.AnimationSplashContent
import com.dwh.gamesapp.splash.presentation.components.DesignSplashScreen

@Composable
fun SplashScreen(isUserLoggedIn: Boolean, navigateTo: () -> Unit) {
    GameBackgroundGradient {
        SplashView(isUserLoggedIn = isUserLoggedIn, navigateTo = navigateTo)
    }
}

@Composable
fun SplashView(isUserLoggedIn: Boolean, navigateTo: () -> Unit) {
    val scaleAnimation: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }
    val rotateAnimation: Animatable<Float, AnimationVector1D> = remember { Animatable(initialValue = 0f) }

    AnimationSplashContent(
        scaleAnimation = scaleAnimation,
        rotateAnimation = rotateAnimation,
        durationMillisAnimation = 1500,
        isUserLoggedIn = isUserLoggedIn,
        navigateTo = navigateTo
    )

    DesignSplashScreen(
        imagePainter = painterResource(id = R.drawable.controller_image),
        scaleAnimation = scaleAnimation,
        rotateAnimation = rotateAnimation
    )
}