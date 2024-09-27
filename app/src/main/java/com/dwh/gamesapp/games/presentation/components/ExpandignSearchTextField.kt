package com.dwh.gamesapp.games.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameSearchTextField

@Composable
fun ExpandingSearchTextField(
    listState: LazyStaggeredGridState,
    value: String,
    onValueChange: (String) -> Unit,
    onClickSearchGames: (String) -> Unit,
    onClickClearTextField: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var isExpanded by remember { mutableStateOf(false) }
    val expansionFraction by animateFloatAsState(targetValue = if (isExpanded) 1f else 0.12f, label = "")
    val iconColor by animateColorAsState(
        targetValue = if (isExpanded) Color.White else MaterialTheme.colorScheme.scrim, label = ""
    )

    LaunchedEffect(isExpanded) {
        if (!isExpanded) focusManager.clearFocus()
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrolling ->
                if (isScrolling) {
                    isExpanded = false
                }
            }
    }

    LaunchedEffect(value) {
        listState.scrollToItem(0)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        GameSearchTextField(
            modifier = Modifier.fillMaxWidth(fraction = expansionFraction),
            isExpanded = isExpanded,
            value = value,
            onValueChange = { onValueChange(it) },
            placeholder = if (expansionFraction == 1f) "Buscar..." else "",
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                    contentDescription = "search icon",
                    tint = iconColor
                )
            },
            onClickLeadingIcon = { isExpanded = !isExpanded },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_backspace),
                    contentDescription = "clear icon"
                )
            },
            onClickTrailingIcon = onClickClearTextField,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onClickSearchGames(value)
                focusManager.clearFocus()
            })
        )
    }
}