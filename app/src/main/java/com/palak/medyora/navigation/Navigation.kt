package com.palak.medyora.navigation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.palak.medyora.Authentication.SignInScreen
import com.palak.medyora.Authentication.SignUpScreen
import com.palak.medyora.screens.MainScreen
import com.palak.medyora.screens.SplashScreen
import com.palak.medyora.screens.UserDetailsScreen
import com.palak.medyora.screens.WelcomeScreen
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Gray900

@Composable
fun App() {
    val navController = rememberNavController()

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = Blue50,
        contentColor = Gray900

    ){
        NavHost(
            navController = navController,
            startDestination = "splash"
        ) {
            composable(route = "splash")  {
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
                        navController.navigate("signin"){
                            popUpTo(0){inclusive=true}
                            launchSingleTop=true
                        }
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
                    },
                    OnNavToSignUpPage = {
                        navController.navigate("signup")
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
}


