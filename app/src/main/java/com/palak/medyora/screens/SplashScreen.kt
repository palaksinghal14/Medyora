package com.palak.medyora.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.White
import com.palak.medyora.viewmodels.SplashState
import com.palak.medyora.viewmodels.SplashViewModel

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
                    colors = listOf(
                        Blue600,
                        Color(0xFF5B7BF0),
                        Blue200
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(  100.dp)
                    .background(
                        color = White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "M",
                        fontSize = 52.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Medyora",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                letterSpacing =1.sp
            )

            Text(
                text = "Your Personal Health Companion ",
                fontSize = 15.sp,
                color = White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.5.sp
            )
        }

        // Loading indicator at bottom

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .width(80.dp)
                    .height(6.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = White,
                trackColor = White.copy(alpha = 0.3f),
                strokeCap = StrokeCap.Round
            )

        }
    }
}