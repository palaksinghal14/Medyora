package com.example.medyora.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medyora.screens.FoodGuideScreen
import com.example.medyora.screens.HomeScreen
import com.example.medyora.screens.ProfileScreen
import com.example.medyora.screens.SettingsScreen
import com.example.medyora.screens.SymptomAnalysisScreen
import com.example.medyora.screens.SymptomAnalysisScreens
import com.example.medyora.viewmodels.MainViewModel

object MainRoutes {
    const val HOME="home"
    const val PROFILE="profile"
    const val SETTINGS="settings"
    const val SYMPTOM="symptom"
    const val FOOD="food"
}


@Composable
fun MainNavGraph(
    mainViewModel: MainViewModel,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainRoutes.HOME
    ) {
        composable(MainRoutes.HOME) {
            HomeScreen(mainViewModel, navController)
        }

        composable(MainRoutes.PROFILE) {
            ProfileScreen()
        }

        composable(MainRoutes.SETTINGS) {
            SettingsScreen(
                { navController.navigate("signup") }
            )
        }

        composable(MainRoutes.SYMPTOM) {
            SymptomAnalysisScreens()
        }

        composable(MainRoutes.FOOD) {
            FoodGuideScreen()

        }
    }

}

