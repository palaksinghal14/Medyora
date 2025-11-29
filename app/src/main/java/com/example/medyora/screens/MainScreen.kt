package com.example.medyora.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue50
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray700


data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        route = "history",
        title = "History",
        icon = Icons.Default.History
    ),
    BottomNavItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    ),
    BottomNavItem(
        route = "settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
)


@Composable
fun MainScreen(
    userName: String
){


    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = bottomNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    userName = userName,
                    OnNavToSymptomAnalysis = {
                        // TODO: navigate to Symptom Analysis screen later
                    },
                    OnNavToFoodAnalysis= {
                        // TODO: navigate to Food Guide screen later
                    },
                    OnNavToDoctorAnalysis= {
                        // TODO: navigate to Doctor Suggestion screen later
                    }
                )
            }

            composable("history") {
                Text(
                    text = "History Screen (coming soon)",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            composable("profile") {
                Text(
                    text = "Profile Screen (coming soon)",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            composable("settings") {
                Text(
                    text = "Settings Screen (coming soon)",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun BottomBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Blue200
    ) {

        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue600,
                    unselectedIconColor = Gray700,
                    selectedTextColor = Blue600,
                    unselectedTextColor = Gray700,
                    indicatorColor = Blue100
                )
            )
        }
    }
}