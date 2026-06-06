package com.palak.medyora.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.palak.medyora.screens.EditProfileRoute
import com.palak.medyora.screens.FoodGuideScreen
import com.palak.medyora.screens.HomeScreen
import com.palak.medyora.screens.NearbyDoctorsScreen
import com.palak.medyora.screens.ProfileRoute
import com.palak.medyora.screens.SettingsScreen
import com.palak.medyora.screens.SymptomAnalysisScreens
import com.palak.medyora.viewmodels.MainViewModel
import com.palak.medyora.viewmodels.ProfileViewModel

object MainRoutes {
    const val HOME="home"
    const val PROFILE="profile"
    const val SETTINGS="settings"
    const val SYMPTOM="symptom"
    const val FOOD="food"
    const val DOCTOR="doctor"
}


@Composable
fun MainNavGraph(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    onNavToSplash : ()->Unit,
    onNavToSignIn : ()->Unit
) {
    NavHost(
        navController = navController,
        startDestination = MainRoutes.HOME
    ) {
        composable(MainRoutes.HOME) {
            HomeScreen(mainViewModel, navController)
        }

        composable(MainRoutes.PROFILE) {backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MainRoutes.PROFILE)
            }
            val viewModel: ProfileViewModel = hiltViewModel(parentEntry) // passing viewmodel here beacuse we want to use same viewoeel instance for bothe the screens , so that both stay updated with the latest data a
            ProfileRoute (
                viewModel=viewModel,
                onEditProfile = {
                    navController.navigate("editprofile")
                }
            )
        }

        composable("editprofile") {backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(MainRoutes.PROFILE)
            }
            val viewModel: ProfileViewModel = hiltViewModel(parentEntry)
            EditProfileRoute (
                viewModel = viewModel,
                onBack  = {
                    navController.popBackStack()
                }
            )
        }

        composable(MainRoutes.SETTINGS) {
            SettingsScreen(
                onNavToSignIn = onNavToSignIn,
                onNavToSplashPage = onNavToSplash
            )
        }

        composable(MainRoutes.SYMPTOM) {
            SymptomAnalysisScreens()
        }

        composable(MainRoutes.FOOD) {
            FoodGuideScreen()
        }

        composable(MainRoutes.DOCTOR) {
            NearbyDoctorsScreen()
        }
    }

}

