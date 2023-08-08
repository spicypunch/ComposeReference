package com.example.composebottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.composebottomnavigation.ui.theme.ComposeBottomNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBottomNavigationTheme {
                App()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val navItems = listOf(Screen.Home, Screen.Profile, Screen.Setting)

    Scaffold(
        bottomBar = {
            BottomNavigation(navController) {
                val navBackStackEntry = navController.currentBackStackEntry
                val currentRoute = navBackStackEntry?.destination?.route

                navItems.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Prevents multiple instances of the same destination when reselecting the selected item
                                popUpTo(screen.route) { saveState = true }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(screen.icon, contentDescription = null) }, // Add any icon you want using screen.icon
                        label = { Text(screen.label) } // Add any label you want using screen.label
                    )
                }
            }
        }
    ) {
        NavHost(navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.Setting.route) { SettingScreen() }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        Bo
    )
}


@Composable
fun HomeScreen() {

}

@Composable
fun ProfileScreen() {

}

@Composable
fun SettingScreen() {

}