package com.dwh.gamesapp.profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.composables.GameModalBottomSheet
import com.dwh.gamesapp.core.presentation.composables.GameOutlinedButton
import com.dwh.gamesapp.core.presentation.theme.secondary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModalBottomSheet(
    themeOptions: List<String>,
    defaultSelected: Int,
    onAccept: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    var selectedOption by remember { mutableIntStateOf(defaultSelected) }

    GameModalBottomSheet(
        bottomSheetState = bottomSheetState,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(id = R.string.profile_modal_bottom_sheet_title),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(15.dp))

            themeOptions.forEach { themeName ->
                ThemeRadioButton(
                    text = themeName,
                    selectedValue = themeOptions[selectedOption]
                ) { selectedValue ->
                    selectedOption = themeOptions.indexOf(selectedValue)
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameFilledButton(nameButton = stringResource(id = R.string.profile_modal_bottom_sheet_btn_accept)) {
                    scope
                        .launch { bottomSheetState.hide() }
                        .invokeOnCompletion { if (!bottomSheetState.isVisible) onAccept(selectedOption) }
                }

                GameOutlinedButton(nameButton = stringResource(id = R.string.profile_modal_bottom_sheet_btn_cancel)) {
                    scope
                        .launch { bottomSheetState.hide() }
                        .invokeOnCompletion { if (!bottomSheetState.isVisible) onDismiss() }
                }
            }
        }
    }
}

@Composable
fun ThemeRadioButton(
    text: String,
    selectedValue: String,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (text == selectedValue),
                onClick = { onClick(text) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (text == selectedValue),
            colors = RadioButtonDefaults.colors(selectedColor = secondary),
            onClick = { onClick(text) }
        )
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}