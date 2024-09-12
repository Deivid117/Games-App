package com.dwh.gamesapp.edit_profile.presentation.components

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameElevatedDialog

@Composable
fun UpdateSuccessDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val mediaPlayer = remember {  MediaPlayer.create(context, R.raw.crash_1_up) }

    DisposableEffect(Unit) {
        mediaPlayer.start()
        onDispose {
            mediaPlayer.release()
        }
    }

    GameElevatedDialog(
        animation = R.raw.heart_animation,
        bodyText = "Se ha modificado tu perfil",
        onDismiss = {
            mediaPlayer.stop()
            onDismiss()
        }
    )
}