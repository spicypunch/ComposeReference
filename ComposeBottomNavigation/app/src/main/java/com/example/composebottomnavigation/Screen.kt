package com.example.composebottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val badgeCount: Int
) {
    object Home: Screen("home", "Home", Icons.Outlined.Home, 3)
    object Profile: Screen("profile", "Profile", Icons.Outlined.AccountCircle, 3)
    object Setting: Screen("setting", "Setting", Icons.Outlined.Settings, 2)
}
