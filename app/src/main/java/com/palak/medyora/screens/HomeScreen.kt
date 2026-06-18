package com.palak.medyora.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.palak.medyora.navigation.MainRoutes
import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray300
import com.palak.medyora.ui.theme.Gray400
import com.palak.medyora.ui.theme.Gray500
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.Green50
import com.palak.medyora.ui.theme.Green600
import com.palak.medyora.ui.theme.Purple100
import com.palak.medyora.ui.theme.Purple600
import com.palak.medyora.ui.theme.White
import com.palak.medyora.viewmodels.MainUiState
import com.palak.medyora.viewmodels.MainViewModel
import java.util.Calendar


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    mainNavController: NavHostController
){
    val uiState =viewModel.uiState.value


        when (uiState) {
            is MainUiState.Loading -> {
                CircularProgressIndicator(color =Blue600)
            }

            is MainUiState.Success -> {
                HomeContent(userName = uiState.userName,mainNavController)
            }

            is MainUiState.Error -> {
                FullScreenError(
                    exception = uiState.message
                )
            }
        }

}

@Composable
fun HomeContent( userName: String , mainNavController: NavHostController)
{
    val mainFeatures = listOf(
        MainFeature(
            id = "symptom-analysis",
            title = "Symptom Analysis",
            description = "Analyze your symptoms and get AI-powered health insights",
            icon = Icons.Default.HealthAndSafety,
            iconColor = Blue600,
            backgroundColor = White,
            borderColor = Blue100,
            onClick ={
                mainNavController.navigate(MainRoutes.SYMPTOM)
            }
        ),
        MainFeature(
            id = "smart-food-guide",
            title = "Smart Food Guide",
            description = "Get personalized nutrition recommendations and meal plans",
            icon = Icons.Default.Restaurant,
            iconColor = Blue600,
            backgroundColor = White,
            borderColor =Blue100,
            onClick = {
                mainNavController.navigate(MainRoutes.FOOD)
            }
        ),
        MainFeature(
            id = "nearby-doctors",
            title = "Nearby Doctors",
            description = "Find healthcare professionals near you ",
            icon = Icons.Default.LocationOn,
            iconColor = Blue600,
            backgroundColor = White,
            borderColor = Blue100,
            onClick = {
                mainNavController.navigate(MainRoutes.DOCTOR)
            }
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color =Blue50),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
        ) {
            // Greeting card — anchored visual at top
            item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = getGreeting(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray900
                            )
                            Text(
                                text = userName.lowercase().replaceFirstChar { it.uppercase() } + "!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                            )
                        }

                        Text(
                            text = "How can I help you today?",
                            fontSize = 14.sp,
                            color = Gray500,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
            }

            // Main Features
            item {

                Text(
                    text = "Main Features",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray700,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(mainFeatures) { feature ->
                MainFeatureCard(
                    feature = feature,
                    onClick = { feature.onClick() }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

        }
    }
}

data class MainFeature(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
    val onClick: () -> Unit

)

// Time-aware greeting
fun getGreeting(): String {
    val hour = Calendar.getInstance().get( Calendar.HOUR_OF_DAY)
    return when {
        hour < 12 -> "Good morning,"
        hour < 17 -> "Good afternoon,"
        else -> "Good evening,"
    }
}

@Composable
private fun MainFeatureCard(
    feature: MainFeature,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = feature.backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, feature.borderColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Blue50),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = feature.iconColor
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = feature.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray900
                )
                Text(
                    text = feature.description,
                    fontSize = 13.sp,
                    color = Gray500,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Gray300
            )
        }
    }
}
