package com.dwh.gamesapp.signup.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.a.presentation.composables.Avatars
import com.dwh.gamesapp.core.presentation.composables.GameModalBottomSheet
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.composables.GameOutlinedButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarModalBottomSheet(
    onAccept: (Long, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedAvatarId by remember { mutableLongStateOf(0L) }
    var selectedAvatarImage by remember { mutableIntStateOf(R.drawable.user) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val avatars = listOf(
        Avatars.Avatar1,
        Avatars.Avatar2,
        Avatars.Avatar3
    )

    GameModalBottomSheet(
        bottomSheetState = bottomSheetState,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            VerticalGridAvatars(
                modifier = Modifier.weight(1f, fill = false),
                avatars = avatars,
                selectedAvatarId = selectedAvatarId
            ) { image, id ->
                selectedAvatarImage = image
                selectedAvatarId = id
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameFilledButton(nameButton = "Accept", enabled = selectedAvatarId != 0L) {
                    scope
                        .launch { bottomSheetState.hide() }
                        .invokeOnCompletion {
                            if (!bottomSheetState.isVisible) onAccept(selectedAvatarId, selectedAvatarImage)
                        }
                }

                GameOutlinedButton(nameButton = "Cancel") {
                    scope
                        .launch { bottomSheetState.hide() }
                        .invokeOnCompletion { if (!bottomSheetState.isVisible) onDismiss() }
                }
            }
        }
    }
}