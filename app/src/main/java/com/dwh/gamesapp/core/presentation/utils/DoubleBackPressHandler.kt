package com.dwh.gamesapp.core.presentation.utils

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun DoubleBackPressHandler() {

    var backPressState by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = backPressState) {
        delay(2000)
        backPressState = false
    }

    BackHandler {
        if (backPressState) {
            (context as? android.app.Activity)?.finishAffinity()
        } else {
            backPressState = true
            Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
        }
    }
}