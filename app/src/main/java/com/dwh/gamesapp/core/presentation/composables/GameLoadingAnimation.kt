package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.dwh.gamesapp.R

@Composable
fun GameLoadingAnimation(
    modifier: Modifier = Modifier,
    size: Int = 300
){
    val isLottiePlaying by remember {
        mutableStateOf(true)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.pacman_animation)
    )
    val lottieAnimation by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLottiePlaying
    )

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CompositionAnimation(composition, lottieAnimation, size)
    }
}

@Composable()
fun CompositionAnimation(
    composition: LottieComposition?,
    lottieAnimation: Float,
    size: Int
){
    LottieAnimation(
        composition = composition,
        progress = { lottieAnimation },
        Modifier.size(size.dp)
    )
}
