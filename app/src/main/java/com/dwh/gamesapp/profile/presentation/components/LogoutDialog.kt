package com.dwh.gamesapp.profile.presentation.components

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameElevatedDialog
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.composables.GameOutlinedButton

@Composable
fun LogoutDialog(
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val mediaPlayer = remember {  MediaPlayer.create(context, R.raw.navi_hey_listen) }

    DisposableEffect(Unit) {
        mediaPlayer.start()
        onDispose {
            mediaPlayer.release()
        }
    }

    GameElevatedDialog(
        animation = R.raw.broken_heart_animation,
        bodyText = "¿Seguro que quieres cerrar sesión?",
        isLogoutDialog = true,
        onDismiss = {
            mediaPlayer.stop()
            onDismiss()
        },
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameFilledButton(
                    nameButton = "Yes",
                    onClick = onAccept
                )

                GameOutlinedButton(
                    nameButton = "No",
                    onClick = onDismiss
                )
            }
        }
    )
}