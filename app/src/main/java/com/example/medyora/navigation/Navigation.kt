package com.example.medyora.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medyora.Authentication.SignInScreen
import com.example.medyora.Authentication.SignUpScreen
import com.example.medyora.screens.MainScreen
import com.example.medyora.screens.SplashScreen
import com.example.medyora.screens.UserDetailsScreen
import com.example.medyora.screens.WelcomeScreen

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
                    navController.navigate("welcome")
                },
                OnNavToMainPage = {
                    navController.navigate("main")
                },
                OnNavToUserDetailsPage = {
                    navController.navigate("userdetails")
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
            MainScreen()
        }
    }
}

