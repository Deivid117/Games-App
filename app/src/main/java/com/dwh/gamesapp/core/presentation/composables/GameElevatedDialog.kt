package com.dwh.gamesapp.core.presentation.composables

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.Dogica

@Composable
fun GameElevatedDialog(
    @RawRes animation: Int = R.raw.pacman_animation,
    bodyText: String,
    isLogoutDialog: Boolean = false,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit = {}
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

    Dialog(onDismissRequest = { onDismiss() }) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                
                CompositionAnimation(composition, lottieAnimation, 100)

                GameTitleGradientText(
                    text = if (!isLogoutDialog) "1up" else "GAME OVER",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = bodyText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )

                if(isLogoutDialog) {
                    Spacer(modifier = Modifier.height(20.dp))

                    content()
                }
            }
        }
    }
}
