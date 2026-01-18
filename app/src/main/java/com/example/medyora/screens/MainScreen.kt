package com.example.medyora.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medyora.navigation.MainNavGraph
import com.example.medyora.navigation.MainRoutes
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray700
import com.example.medyora.viewmodels.MainUiState
import com.example.medyora.viewmodels.MainViewModel

@Composable
fun MainScreen(){
    val mainViewModel: MainViewModel= hiltViewModel()
    val mainNavController : NavHostController =rememberNavController()


    Scaffold (
        bottomBar = {
            MainBottomBar(mainNavController)
        }
    ){
        paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            MainNavGraph(mainViewModel ,mainNavController)
        }

    }
}

@Composable
fun MainBottomBar(
    mainNavController: NavHostController
)
{
    val currentBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute= currentBackStackEntry?.destination?.route

   NavigationBar(
       containerColor = Blue200
   ) {
       NavigationBarItem(
           selected = currentRoute== MainRoutes.HOME,
           onClick = {
               mainNavController.navigate(MainRoutes.HOME){
                   launchSingleTop=true
               }
           },
           label = {Text(text = "Home")},
           icon = {
               Icon(Icons.Default.Home,"home")},
           colors = NavigationBarItemDefaults.colors(
               selectedIconColor = Blue600,
               unselectedIconColor = Gray700,
               selectedTextColor = Blue600,
               unselectedTextColor = Gray700,
               indicatorColor = Blue100
           )

       )
       NavigationBarItem(
           selected = currentRoute== MainRoutes.PROFILE,
           onClick = {
               mainNavController.navigate(MainRoutes.PROFILE){
                   launchSingleTop=true
               }
           },
           label = {Text(text = "Profile")},
           icon = {
               Icon(Icons.Default.Person,"Profile")},
           colors = NavigationBarItemDefaults.colors(
               selectedIconColor = Blue600,
               unselectedIconColor = Gray700,
               selectedTextColor = Blue600,
               unselectedTextColor = Gray700,
               indicatorColor = Blue100
           )
       )
       NavigationBarItem(
           selected = currentRoute== MainRoutes.SETTINGS,
           onClick = {
               mainNavController.navigate(MainRoutes.SETTINGS){
                   launchSingleTop=true
               }
           },
           label = {Text(text = "Settings")},
           icon = {
               Icon(Icons.Default.Settings,"Settings")},
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




@Composable
fun ProfileScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Profile Screen")
    }
}




@Composable
fun SymptomAnalysisScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Symptom Analysis (Coming Next)")
    }
}

@Composable
fun FoodGuideScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Food Guide (Coming Next)")
    }
}
/*

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
                       bottomNavController.navigate("symptomanalysis")
                    },
                    OnNavToFoodAnalysis= {
                        // TODO: navigate to Food Guide screen later
                    },
                    OnNavToDoctorAnalysis= {
                        // TODO: navigate to Doctor Suggestion screen later
                    }
                )
            }
            composable("symptomanalysis"){
                SymptomAnalysisScreen(
                    onBack = {

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
*/
