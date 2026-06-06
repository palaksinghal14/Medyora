package com.palak.medyora.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.palak.medyora.navigation.MainNavGraph
import com.palak.medyora.navigation.MainRoutes
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.viewmodels.MainViewModel

@Composable
fun MainScreen(
    onNavToSplash : ()->Unit ,
    onNavToSignIn: () -> Unit
){
    val mainViewModel: MainViewModel= hiltViewModel()
    val mainNavController : NavHostController =rememberNavController()


    Scaffold (
        bottomBar = {
            MainBottomBar(mainNavController)
        }
    ){
        paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)){
            MainNavGraph(mainViewModel ,mainNavController, onNavToSplash , onNavToSignIn)
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
