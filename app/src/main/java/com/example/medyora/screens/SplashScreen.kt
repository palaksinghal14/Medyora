package com.example.medyora.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.viewmodels.SplashState
import com.example.medyora.viewmodels.SplashViewModel
import com.example.medyora.viewmodels.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    OnNavToWelcomePage: ()-> Unit,
    OnNavToUserDetailsPage: () -> Unit,
    OnNavToMainPage: () -> Unit
){
    val splashViewModel: SplashViewModel = hiltViewModel()
    val  state = splashViewModel.splashstate.value

    LaunchedEffect(state) {
        when (state) {
            is SplashState.GoToWelcome -> OnNavToWelcomePage()
            is SplashState.GoToUserDetails -> OnNavToUserDetailsPage()
            is SplashState.GoToMain -> OnNavToMainPage()
            else -> {} // Loading state, do nothing

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Blue200, Blue100)
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //replace with actual logo
            Card(
                modifier = Modifier.size(  100.dp),
                colors = CardDefaults.cardColors(containerColor = Blue600),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "M",
                        fontSize = 45.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Medyora",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Blue600
            )

            Text(
                text = "Your Health Companion",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Loading indicator at bottom
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .width(100.dp)
                    .height(6.dp),
                color = Blue600,
                strokeCap = StrokeCap.Square
            )




            Text(
                text = "Loading...",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}