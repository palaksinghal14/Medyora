
package com.palak.medyora.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.model.SymptomAnalysis.RiskLevel
import com.palak.medyora.model.SymptomAnalysis.SymptomDuration
import com.palak.medyora.model.SymptomAnalysis.SymptomSeverity
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray300
import com.palak.medyora.ui.theme.Gray400
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.Orange500
import com.palak.medyora.ui.theme.Purple600
import com.palak.medyora.ui.theme.Red50
import com.palak.medyora.ui.theme.White
import com.palak.medyora.viewmodels.SymptomFlowState
import com.palak.medyora.viewmodels.SymptomInputUiState
import com.palak.medyora.viewmodels.SymptomViewModel


@Composable
fun SymptomAnalysisScreens(){

val symptomViewModel : SymptomViewModel= hiltViewModel()
    val uiState by symptomViewModel.uiState.collectAsState()
    val flowState by symptomViewModel.flowState.collectAsState()


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
        when(flowState){
            is SymptomFlowState.Idle -> SymptomInputScreen(uiState,symptomViewModel)
            is SymptomFlowState.Loading -> CircularProgressIndicator()
            is SymptomFlowState.Error -> {
                Text(text = (flowState as SymptomFlowState.Error).message)
            }
            is SymptomFlowState.Result -> SymptomResultScreen(flowState as SymptomFlowState.Result,symptomViewModel)
            is SymptomFlowState.FollowUp -> SymptomFollowUpScreen(flowState as SymptomFlowState.FollowUp,symptomViewModel)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomInputScreen( uiState: SymptomInputUiState, viewModel: SymptomViewModel) {
    var durationExpanded by remember { mutableStateOf(false) }
    var severityExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 4.dp,
                color = White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment =
                        Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Gray900
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "Symptom Analysis",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                            )
                            Text(
                                text = "Personalised Health Insights",
                                fontSize = 14.sp,
                                color = Gray600
                            )
                        }
                    }

                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            //message
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Blue200)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Blue600, Purple600)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "How are you feeling today?",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray900
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Describe your symptoms in detail, and I'll provide personalized health insights, possible causes, and care recommendations.",
                                    fontSize = 14.sp,
                                    color = Gray600,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }


            //input
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Blue200)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Blue50, Blue200)
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Thermostat,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Detailed Description",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray900
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            border = androidx.compose.foundation.BorderStroke(2.dp, Blue200)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                // Symptom Text Input
                                OutlinedTextField(
                                    value = uiState.symptomText,
                                    onValueChange = { viewModel.onSymptomTextChange(it) },
                                    label = { Text("Symptoms") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                // Duration Dropdown

                                val durations = SymptomDuration.values().toList()

                                ExposedDropdownMenuBox(
                                    expanded = durationExpanded,
                                    onExpandedChange = { durationExpanded = !durationExpanded }
                                ) {
                                    OutlinedTextField(
                                        value = uiState.duration?.name ?: "",
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Duration") },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = durationExpanded)
                                        },
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = durationExpanded,
                                        onDismissRequest = { durationExpanded = false }
                                    ) {
                                        durations.forEach { d ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(text = d.name)
                                                },
                                                onClick = {
                                                    viewModel.onDurationSelected(d)
                                                    durationExpanded = false
                                                }
                                            )

                                        }
                                    }
                                }

                                // severity Dropdown

                                val severities = SymptomSeverity.values().toList()

                                ExposedDropdownMenuBox(
                                    expanded = severityExpanded,
                                    onExpandedChange = { severityExpanded = !severityExpanded }
                                ) {
                                    OutlinedTextField(
                                        value = uiState.severity?.name ?: "",
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text("Severity") },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = severityExpanded)
                                        },
                                        modifier = Modifier
                                            .menuAnchor()
                                            .fillMaxWidth()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = severityExpanded,
                                        onDismissRequest = { severityExpanded = false }
                                    ) {
                                        severities.forEach { s ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(text = s.name)
                                                },
                                                onClick = {
                                                    viewModel.onSeveritySelected(s)
                                                    severityExpanded = false
                                                }
                                            )

                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))


                                // button
                                Button(
                                    onClick = {
                                        viewModel.analyzeSymptom()
                                    },
                                    colors = ButtonDefaults.buttonColors(Blue200)
                                ) {

                                    Row(horizontalArrangement = Arrangement.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Psychology,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Analyze Symptoms", fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }

                }
            }

            // Medical Disclaimer
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Blue50,
                    border = androidx.compose.foundation.BorderStroke(2.dp, Purple600)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Purple600,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Medical Disclaimer",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Red50
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "This analysis is for informational purposes only and does not replace professional medical advice. Always consult with a healthcare provider for accurate diagnosis and treatment.",
                                fontSize = 12.sp,
                                color = Orange500,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomResultScreen(
    flowState: SymptomFlowState.Result,
    viewModel: SymptomViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Symptom Analysis", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.resetSymptomFlow() }) {
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

            // ✅ SUCCESS CARD
            item {
                Card(
                    modifier = Modifier.
                    fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Analysis Complete",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )

                        Text(
                            "Here's what we found based on your symptoms",
                            fontSize = 14.sp,
                            color = Gray700
                        )
                    }
                }
            }

            // ✅ RISK LEVEL CARD
            item {
                Card(
                    modifier = Modifier.
                    fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "Severity Assessment",
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            flowState.riskLevel.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = when(flowState.riskLevel) {
                                RiskLevel.LOW -> Color.Green
                                RiskLevel.MODERATE -> Color(0xFFFFA000)
                                RiskLevel.HIGH -> Color.Red
                            }
                        )
                    }
                }
            }

            // ✅ CAUSES
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        //  Highlighted Header
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE3F2FD)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    "Possible Causes",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0D47A1)
                                )
                                Text(
                                    "Based on your symptoms",
                                    fontSize = 12.sp,
                                    color = Gray700
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        //  Each cause as separate card
                        flowState.causes.forEach { cause ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                border = BorderStroke(1.dp, Color(0xFFBBDEFB))
                            ) {
                                Text(
                                    text = cause,
                                    modifier = Modifier.padding(14.dp),
                                    fontSize = 14.sp,
                                    color = Gray900
                                )
                            }
                        }
                    }
                }
            }
            // ✅ RECOMMENDATIONS
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        //  Highlighted Header
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE8F5E9)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    "Recommendations",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20)
                                )
                                Text(
                                    "Self-care suggestions",
                                    fontSize = 12.sp,
                                    color = Gray700
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        //  Each recommendation as card
                        flowState.recommendation.forEach { rec ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                border = BorderStroke(1.dp, Color(0xFFC8E6C9))
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("• ", color = Color(0xFF2E7D32))
                                    Text(
                                        text = rec,
                                        fontSize = 14.sp,
                                        color = Gray900
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ✅ BUTTON
            item {
                Button(
                    onClick = { viewModel.resetSymptomFlow() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Analyze Another Symptom")
                }
            }
        }
    }
}

@Composable
fun SymptomFollowUpScreen(
    flowState: SymptomFlowState.FollowUp,
    viewModel: SymptomViewModel
) {
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Quick Follow-Up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )

                    Text(
                        text = flowState.question,
                        fontSize = 16.sp,
                        color = Gray700
                    )

                    flowState.options?.forEach { option ->

                        val isSelected = selectedOption == option

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedOption = option
                                },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Blue200 else White
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) Blue600 else Gray300
                            )
                        ) {
                            Text(
                                text = option,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 14.sp,
                                color = if (isSelected) White else Gray900
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            selectedOption?.let {
                                viewModel.submitFollowUpAnswer(it)
                            }
                        },
                        enabled = selectedOption != null,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedOption != null) Blue600 else Gray400
                        )
                    ) {
                        Text("Continue")
                    }
                }
            }
        }
    }
}
