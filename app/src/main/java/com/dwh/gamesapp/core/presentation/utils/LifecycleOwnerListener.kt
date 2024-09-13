package com.dwh.gamesapp.core.presentation.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun LifecycleOwnerListener() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val systemUi = rememberSystemUiController()
    val isSystemDarkTheme = isDarkThemeEnabled()
    val colorStatusBar = MaterialTheme.colorScheme.primary

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    coroutineScope.launch {
                        systemUi.setStatusBarColor(colorStatusBar, isSystemDarkTheme)
                    }
                }

                Lifecycle.Event.ON_DESTROY -> {
                    coroutineScope.launch {
                        systemUi.setStatusBarColor(colorStatusBar, isSystemDarkTheme)
                    }
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}