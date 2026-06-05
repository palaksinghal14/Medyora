package com.palak.medyora.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Smart Food Guide", fontWeight = FontWeight.Bold)
                        Text("Check any food instantly", fontSize = 12.sp)
                    }
                },
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
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Shield, contentDescription = null)

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
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("What do you want to eat?", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = uiState.foodText,
                            onValueChange = { viewModel.onFoodChange(it) },
                            label = { Text("Enter food item") },
                            modifier = Modifier.fillMaxWidth()
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
                                Icon(Icons.Default.Lightbulb, contentDescription = null)
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
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Info, contentDescription = null)
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

    val riskColor = when (flowState.riskLevel) {
        FoodRiskLevel.SAFE -> Color(0xFF2E7D32)
        FoodRiskLevel.RISKY -> Color(0xFFFFA000)
        FoodRiskLevel.UNSAFE -> Color(0xFFC62828)
        FoodRiskLevel.CAUTION -> Color(0xFF123D06)
    }

    val icon = when (flowState.riskLevel) {
        FoodRiskLevel.SAFE -> Icons.Default.CheckCircle
        FoodRiskLevel.RISKY -> Icons.Default.Warning
        FoodRiskLevel.UNSAFE -> Icons.Default.Close
        FoodRiskLevel.CAUTION -> Icons.Default.Info
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Smart Food Guide", fontWeight = FontWeight.Bold)
                        Text("Check any food instantly", fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.resetFoodFlow() }) {
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

            // ✅ MAIN RESULT CARD
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = riskColor as Color),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(flowState.riskLevel.name, fontWeight = FontWeight.Bold, color =Color.White)
                    }
                }
            }

            // ✅ SUMMARY
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Info, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Summary", fontWeight = FontWeight.Bold)
                            Text(flowState.summary)
                        }
                    }
                }
            }

            // ✅ IMPACTS
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Default.MonitorHeart, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Impact on your health", fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        flowState.impacts.forEach {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(10.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Text(it, modifier = Modifier.padding(12.dp))
                            }
                        }
                    }
                }
            }

            // ✅ PORTION
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Restaurant, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Portion Guidance", fontWeight = FontWeight.Bold)
                            Text(flowState.portion)
                        }
                    }
                }
            }

            // ✅ ALTERNATIVES
            item {
                Card(shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Icon(Icons.Default.Moving, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Better Alternatives", fontWeight = FontWeight.Bold)
                        }

                        Text("Better Alternatives", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        flowState.alternatives.forEach {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(10.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Text(it, modifier = Modifier.padding(12.dp))
                            }
                        }
                    }
                }
            }

            // ✅ BUTTON
            item {
                Button(
                    onClick = { viewModel.resetFoodFlow() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Check another food")
                }
            }
        }
    }
}