package com.dwh.gamesapp.profile.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.utils.Avatars
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.GameUserImage
import com.dwh.gamesapp.core.presentation.utils.shapes.wavyShape

@Composable
fun ProfileUserAvatar(
    profileAvatarId: Long,
    onSetAvatar: (Int) -> Unit
) {

    val avatars = listOf(
        Avatars.Avatar1,
        Avatars.Avatar2,
        Avatars.Avatar3
    )

    var userProfileAvatar by remember { mutableIntStateOf(R.drawable.user) }

    for (i in avatars.indices) {
        if (avatars[i].id == profileAvatarId) {
            userProfileAvatar = avatars[i].image
            onSetAvatar(userProfileAvatar)
        }
    }

    GameBackgroundGradient(
        modifier = Modifier
            .fillMaxHeight(0.4f)
            .graphicsLayer {
                clip = true
                shape = wavyShape()
            }
    ) {
        GameUserImage(modifier = Modifier.padding(top = 50.dp), image = userProfileAvatar)
    }
}