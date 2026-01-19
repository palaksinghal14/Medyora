package com.example.medyora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.medyora.navigation.MainRoutes
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue50
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray400
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.ui.theme.Green50
import com.example.medyora.ui.theme.Green600
import com.example.medyora.ui.theme.Purple50
import com.example.medyora.ui.theme.Purple600
import com.example.medyora.viewmodels.MainUiState
import com.example.medyora.viewmodels.MainViewModel


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    mainNavController: NavHostController
){
    val uiState =viewModel.uiState.value


        when (uiState) {
            is MainUiState.Loading -> {
                CircularProgressIndicator()
            }

            is MainUiState.Success -> {
                HomeContent(userName = uiState.userName,mainNavController)
            }

            is MainUiState.Error -> {
                Text(text = uiState.message)
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
            backgroundColor = Blue50,
            borderColor = Blue200,
            onClick ={
                mainNavController.navigate(MainRoutes.SYMPTOM)
            }
        ),
        MainFeature(
            id = "smart-food-guide",
            title = "Smart Food Guide",
            description = "Get personalized nutrition recommendations and meal plans",
            icon = Icons.Default.Restaurant,
            iconColor = Green600,
            backgroundColor = Green50,
            borderColor = Green600.copy(alpha = 0.3f),
            onClick = {
                mainNavController.navigate(MainRoutes.FOOD)
            }
        ))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Blue200, Blue100)
                )
            ),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column {
                    Text(
                        text = "Good morning, $userName!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                    Text(
                        text = "How can I help you today?",
                        fontSize = 16.sp,
                        color = Gray600,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Main Features
            item {
                Column {
                    Text(
                        text = "Main Features",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray900,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        mainFeatures.forEach { feature ->
                            MainFeatureCard(
                                feature = feature,
                                onClick = {
                                    feature.onClick()
                                }
                            )
                        }
                    }
                }
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



@Composable
private fun MainFeatureCard(
    feature: MainFeature,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = feature.backgroundColor),
        border = androidx.compose.foundation.BorderStroke(2.dp, feature.borderColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(feature.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
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
                    fontWeight = FontWeight.Medium,
                    color = Gray900
                )
                Text(
                    text = feature.description,
                    fontSize = 14.sp,
                    color = Gray600,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Gray400
            )
        }
    }
}
