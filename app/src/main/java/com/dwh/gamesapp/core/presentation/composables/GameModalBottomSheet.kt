package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameModalBottomSheet(
    bottomSheetState: SheetState,
    onDismiss: () -> Unit,
    dragHandle: @Composable() (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable() (() -> Unit)
) {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues()
        .calculateBottomPadding().value.toInt() + 8

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        dragHandle = dragHandle
    ) {
        Box(Modifier.padding(bottom = bottomPadding.dp)) { content() }
    }
}