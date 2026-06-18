package com.palak.medyora.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.model.FoodAnalysis.FoodRiskLevel
import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.ui.components.InlineError
import com.palak.medyora.ui.components.MedyoraTopBar
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray500
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.Green100
import com.palak.medyora.ui.theme.Green50
import com.palak.medyora.ui.theme.Green600
import com.palak.medyora.ui.theme.Orange100
import com.palak.medyora.ui.theme.Orange500
import com.palak.medyora.ui.theme.Red100
import com.palak.medyora.ui.theme.Red600
import com.palak.medyora.ui.theme.White
import com.palak.medyora.ui.theme.Yellow100
import com.palak.medyora.ui.theme.Yellow500
import com.palak.medyora.viewmodels.FoodFlowState
import com.palak.medyora.viewmodels.FoodInputUiState
import com.palak.medyora.viewmodels.FoodViewModel



@Composable
fun FoodGuideScreen() {

    val foodViewModel: FoodViewModel= hiltViewModel()

    val uiState by foodViewModel.uiState.collectAsState()
    val flowState by foodViewModel.flowState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when(flowState){
            is FoodFlowState.Loading-> CircularProgressIndicator()
            is FoodFlowState.Idle-> FoodInputScreen( uiState , foodViewModel)
            is FoodFlowState.Result -> FoodResultScreen(
                flowState as FoodFlowState.Result,
                foodViewModel
            )
            is FoodFlowState.Error -> {
                FullScreenError(
                    exception = (flowState as FoodFlowState.Error).message,
                    onRetry = {foodViewModel.resetFoodFlow()}
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInputScreen(
    uiState: FoodInputUiState,
    viewModel: FoodViewModel
){
    Scaffold(
           modifier = Modifier.statusBarsPadding(),
                topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Smart Food Guide", fontWeight = FontWeight.Bold)
                        Text("Check any food instantly", fontSize = 12.sp)
                    }
                },
                windowInsets = WindowInsets(0),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ✅ PROFILE CARD
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(White),
                    border = BorderStroke(1.dp, Blue100),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Shield, contentDescription = null , tint = Blue600)

                        Column {
                            Text("Your Health Profile", fontWeight = FontWeight.Bold)
                            Text("Analyze food based on your conditions", fontSize = 12.sp)
                        }
                    }
                }
            }

            // ✅ INPUT CARD
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(White),
                    border = BorderStroke(1.dp, Blue100),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("What do you want to eat?", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uiState.foodText,
                            onValueChange = { viewModel.onFoodChange(it) },
                            label = { Text("Enter food item") },
                            modifier = Modifier.fillMaxWidth(),
                            colors= OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Blue600,
                                unfocusedBorderColor = Blue100,
                                focusedLabelColor = Blue600,
                                unfocusedLabelColor = Gray500,
                                cursorColor = Blue600,
                                focusedTextColor = Gray900,
                                unfocusedTextColor = Gray900,
                                focusedContainerColor = White,
                                unfocusedContainerColor = White
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        uiState.error?.let {exception->
                            InlineError(exception=exception)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Button(
                            onClick = { viewModel.analyzeFood() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Check this food")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // QUICK TIP
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp)) {
                                Icon(Icons.Outlined.Lightbulb, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Be specific. Instead of 'snack', try 'potato chips' or 'apple slices'",
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // ✅ HOW IT WORKS
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(White),
                    border = BorderStroke(1.dp, Blue100),

                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Info, contentDescription = null , tint = Blue600)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Our system analyzes the food against your health conditions and provides personalized recommendations, alternatives, and portion guidance.",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodResultScreen(
    flowState: FoodFlowState.Result,
    viewModel: FoodViewModel
) {
    // Risk level semantic colors from design system
    val riskContainerColor = when (flowState.riskLevel) {
        FoodRiskLevel.SAFE -> Green100
        FoodRiskLevel.RISKY -> Yellow100
        FoodRiskLevel.CAUTION -> Orange100
        FoodRiskLevel.UNSAFE -> Red100
    }

    val riskTextColor = when (flowState.riskLevel) {
        FoodRiskLevel.SAFE -> Green600
        FoodRiskLevel.RISKY -> Yellow500
        FoodRiskLevel.CAUTION -> Orange500
        FoodRiskLevel.UNSAFE -> Red600
    }

    val riskBorderColor = when (flowState.riskLevel) {
        FoodRiskLevel.SAFE -> Green100
        FoodRiskLevel.RISKY -> Yellow100
        FoodRiskLevel.CAUTION -> Orange100
        FoodRiskLevel.UNSAFE -> Red100
    }

    val riskIcon = when (flowState.riskLevel) {
        FoodRiskLevel.SAFE -> Icons.Default.CheckCircle
        FoodRiskLevel.RISKY -> Icons.Default.Warning
        FoodRiskLevel.CAUTION -> Icons.Default.Info
        FoodRiskLevel.UNSAFE -> Icons.Default.Close
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        contentWindowInsets = WindowInsets(0),
        containerColor = Blue50,
        topBar = {
            MedyoraTopBar(
                title = "Smart Food Guide",
                subtitle = "Check any food instantly",
                onBack = { viewModel.resetFoodFlow() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            // Risk level card — light background, colored text
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = riskContainerColor),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, riskBorderColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = riskIcon,
                            contentDescription = null,
                            tint = riskTextColor,
                            modifier = Modifier.size(36.dp)
                        )
                        Column {
                            Text(
                                text = flowState.riskLevel.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = riskTextColor
                            )
                            Text(
                                text = "Food safety assessment",
                                fontSize = 12.sp,
                                color = riskTextColor.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Summary card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, Blue100)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Blue50, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Summary",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray900
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = flowState.summary,
                            fontSize = 14.sp,
                            color = Gray600,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Impacts card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, Blue100)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Blue50, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.MonitorHeart,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Impact on your health",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray900
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        flowState.impacts.forEach { impact ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Blue50),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Text(
                                    text = impact,
                                    modifier = Modifier.padding(12.dp),
                                    fontSize = 14.sp,
                                    color = Gray900
                                )
                            }
                        }
                    }
                }
            }

            // Portion card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, Blue100)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Blue50, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Restaurant,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Portion Guidance",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray900
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = flowState.portion,
                            fontSize = 14.sp,
                            color = Gray600,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Alternatives card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, Blue100)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Green50, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Moving,
                                    contentDescription = null,
                                    tint = Green600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Text(
                                text = "Better Alternatives",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray900
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        flowState.alternatives.forEach { alternative ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Green50),
                                elevation = CardDefaults.cardElevation(0.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Green600,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = alternative,
                                        fontSize = 14.sp,
                                        color = Gray900
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Button
            item {
                Button(
                    onClick = { viewModel.resetFoodFlow() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Check another food",
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )
                }
            }
        }
    }
}