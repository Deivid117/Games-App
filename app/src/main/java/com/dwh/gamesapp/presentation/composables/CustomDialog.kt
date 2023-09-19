package com.dwh.gamesapp.presentation.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dwh.gamesapp.R
import com.dwh.gamesapp.presentation.ui.theme.Dogica

@Composable
fun CustomDialog(
    onDissmiss: () -> Unit,
    @RawRes animation: Int = R.raw.pacman_animation,
    title: String,
    isLoggingOut: Boolean = false,
    content: @Composable () -> Unit
) {
    val isLottiePlaying by remember {
        mutableStateOf(true)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation)
    )
    val lottieAnimation by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isLottiePlaying
    )
    
    Dialog(onDismissRequest = { onDissmiss() }) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                CompositionAnimation(composition, lottieAnimation, 100)

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if(!isLoggingOut) "1up" else "GAME OVER",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = Dogica)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )

                if(isLoggingOut) {
                    Spacer(modifier = Modifier.height(20.dp))
                    content()
                }
            }
        }
    }
}
