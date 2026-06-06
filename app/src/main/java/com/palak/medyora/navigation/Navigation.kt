package com.palak.medyora.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.palak.medyora.Authentication.SignInScreen
import com.palak.medyora.Authentication.SignUpScreen
import com.palak.medyora.screens.MainScreen
import com.palak.medyora.screens.SplashScreen
import com.palak.medyora.screens.UserDetailsScreen
import com.palak.medyora.screens.WelcomeScreen

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable(route = "splash") {
            SplashScreen(
                OnNavToWelcomePage = {
                    navController.navigate("welcome"){
                        popUpTo("splash") { inclusive = true }
                    }
                },
                OnNavToMainPage = {
                    navController.navigate("main"){
                        popUpTo("splash") { inclusive = true }
                    }
                },
                OnNavToUserDetailsPage = {
                    navController.navigate("userdetails"){
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("welcome") {
            WelcomeScreen(
                OnNavToSignUpPage = {
                    navController.navigate("signup")
                }
            )
        }
        composable("signup") {
            SignUpScreen(
                OnNavToSignInPage = {
                    navController.navigate("signin")
                },
                OnNavToUserDetailsPage = {
                    navController.navigate("userdetails")
                }
            )
        }
        composable("signin") {
            SignInScreen(
                OnNavToMainPage = {
                    navController.navigate("main")
                }
            )
        }
        composable("userdetails") {
            UserDetailsScreen(
                OnNavToMainPage = {
                    navController.navigate("main")
                }
            )
        }

        composable("main") {
            MainScreen(
                onNavToSplash={
                    navController.navigate("splash"){
                        popUpTo(0){inclusive=true}
                        launchSingleTop=true
                    }
                },
                onNavToSignIn = {
                    navController.navigate("signin"){
                        popUpTo(0){inclusive=true}
                        launchSingleTop=true
                    }
                }
            )
        }
    }
}

