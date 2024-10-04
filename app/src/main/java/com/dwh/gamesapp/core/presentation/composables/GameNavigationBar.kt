package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dwh.gamesapp.core.presentation.navigation.BottomScreens
import com.dwh.gamesapp.core.presentation.theme.dark_nav_bar_background
import com.dwh.gamesapp.core.presentation.theme.dark_nav_item_background
import com.dwh.gamesapp.core.presentation.theme.light_nav_bar_background
import com.dwh.gamesapp.core.presentation.theme.tertiary
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled

@Composable
fun GameNavigationBar(navController: NavController) {
    val screens = listOf(
        BottomScreens.Home,
        BottomScreens.Games,
        BottomScreens.Favorites,
        BottomScreens.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationBarBackgroundColor =
        if (isDarkThemeEnabled()) dark_nav_bar_background
        else light_nav_bar_background

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 5.dp)
            .padding(bottom = 5.dp)
            .navigationBarsPadding(),
        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(containerColor = navigationBarBackgroundColor)
    ) {
        Row(
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                GameNavigationBarItem(
                    navController = navController,
                    screen = screen,
                    currentDestination = currentDestination
                )
            }
        }
    }
}

@Composable
fun GameNavigationBarItem(
    navController: NavController,
    screen: BottomScreens,
    currentDestination: NavDestination?
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    val iconColor = if (isDarkThemeEnabled()) tertiary else Color.White
    val iconColorItem =
        if (selected) iconColor else MaterialTheme.colorScheme.onBackground
    val backgroundColor = if (isDarkThemeEnabled()) dark_nav_item_background else tertiary
    val backgroundColorItem =
        if (selected) backgroundColor else Color.Transparent

    Box(
        modifier = Modifier
            .height(35.dp)
            .clip(CircleShape)
            .background(backgroundColorItem)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = screen.icon),
                contentDescription = "nav item icon",
                tint = iconColorItem
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = iconColorItem,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
