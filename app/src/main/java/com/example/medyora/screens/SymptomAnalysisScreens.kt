
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.medyora.model.SymptomAnalysis.SymptomDuration
import com.example.medyora.model.SymptomAnalysis.SymptomSeverity
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue50
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.ui.theme.Orange500
import com.example.medyora.ui.theme.Purple600
import com.example.medyora.ui.theme.Red50
import com.example.medyora.ui.theme.White
import com.example.medyora.viewmodels.SymptomFlowState
import com.example.medyora.viewmodels.SymptomInputUiState
import com.example.medyora.viewmodels.SymptomViewModel


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
            is SymptomFlowState.Error -> Text(" failed ")
            is SymptomFlowState.Result -> SymptomResultScreen(flowState as SymptomFlowState.Result)
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
        }
    }
}


@Composable
fun SymptomResultScreen(
    flowState: SymptomFlowState.Result
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Analysis Result", style = MaterialTheme.typography.titleMedium)

        Text("Causes:", style = MaterialTheme.typography.bodyLarge)
        flowState.causes.forEach { cause -> Text("• $cause") }

        Text("Risk Level: ${flowState.riskLevel}", style = MaterialTheme.typography.bodyLarge)

        Text("Recommendations:", style = MaterialTheme.typography.bodyLarge)
        flowState.recommendation.forEach { rec -> Text("• $rec") }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick ={}, modifier = Modifier.fillMaxWidth()) {
            Text("Analyze Another Symptom")
        }
    }
}

@Composable
fun SymptomFollowUpScreen(
    flowState: SymptomFlowState.FollowUp,
    viewModel: SymptomViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Follow-Up Question",
            style = MaterialTheme.typography.titleMedium
        )
        Text(flowState.question, style = MaterialTheme.typography.bodyLarge)

        flowState.options?.forEach { option ->
            Button(
                onClick = {
                    viewModel.submitFollowUpAnswer( option)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(option)
            }
        }
    }
}

/*
data class CommonSymptom(
    val name: String,
    val emoji: String
)

data class PossibleCause(
    val name: String,
    val probability: Int,
    val description: String,
    val emoji: String
)

data class WhenToSeeDoctor(
    val urgency: String, // "immediate", "soon", "routine"
    val timeframe: String,
    val reasons: List<String>
)

data class SelfCareTip(
    val category: String,
    val tip: String,
    val emoji: String
)

data class AnalysisResult(
    val seriousnessLevel: Int,
    val seriousnessLabel: String,
    val possibleCauses: List<PossibleCause>,
    val whenToSeeDoctor: WhenToSeeDoctor,
    val selfCareTips: List<SelfCareTip>
)

// for doctor urgency
data class UrgencyUiConfig(
    val gradientColors: List<Color>,
    val borderColor: Color,
    val badgeColor: Color,
    val emoji: String,
    val urgencyLabel: String
)

@Composable
fun SymptomAnalysisScreen(
    onBack: () -> Unit
) {
    var symptomText by remember { mutableStateOf("") }
    var isRecording by remember { mutableStateOf(false) }
    var uploadedImageCount by remember { mutableStateOf(0) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisResult by remember { mutableStateOf<AnalysisResult?>(null) }
    var selectedSymptoms by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedTab by remember { mutableStateOf(0) } // 0=Text, 1=Voice, 2=Image

    val commonSymptoms = listOf(
        CommonSymptom("Headache", "🤕"),
        CommonSymptom("Fever", "🤒"),
        CommonSymptom("Cough", "😷"),
        CommonSymptom("Fatigue", "😴"),
        CommonSymptom("Nausea", "🤢"),
        CommonSymptom("Dizziness", "😵"),
        CommonSymptom("Sore Throat", "😣"),
        CommonSymptom("Body Ache", "💪")
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
                        IconButton(onClick = onBack) {
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
                                text = "AI-powered health insights",
                                fontSize = 14.sp,
                                color = Gray600
                            )
                        }
                    }
                    if (analysisResult == null) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Blue50,
                            border = androidx.compose.foundation.BorderStroke(1.dp,color= Color.Gray)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MedicalServices,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "Ready",
                                    fontSize = 12.sp,
                                    color = Blue500,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Blue50,Blue200, Purple50)
                    )
                 )
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (analysisResult == null) {
                // Welcome Card
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
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Blue50, Blue200, Purple50)
                                    )
                                )
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

                // Quick Symptom Selection
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Purple600)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Purple80, Pink80)
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = Purple600,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Quick Select Common Symptoms",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Gray900
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Symptoms Grid
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                for (row in 0..3) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        for (col in 0..1) {
                                            val index = row * 2 + col
                                            if (index < commonSymptoms.size) {
                                                val symptom = commonSymptoms[index]
                                                val isSelected = selectedSymptoms.contains(symptom.name)

                                                Surface(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .clickable {
                                                            selectedSymptoms = if (isSelected) {
                                                                selectedSymptoms - symptom.name
                                                            } else {
                                                                selectedSymptoms + symptom.name
                                                            }
                                                        },
                                                    shape = RoundedCornerShape(12.dp),
                                                    color = if (isSelected) Purple50 else Color.White,
                                                    border = androidx.compose.foundation.BorderStroke(
                                                        2.dp,
                                                        if (isSelected) Purple600 else Gray200
                                                    ),
                                                    shadowElevation = if (isSelected) 8.dp else 0.dp
                                                ) {
                                                    Row(
                                                        modifier = Modifier.padding(12.dp),
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        Text(
                                                            text = symptom.emoji,
                                                            fontSize = 24.sp
                                                        )
                                                        Text(
                                                            text = symptom.name,
                                                            fontSize = 14.sp,
                                                            fontWeight = FontWeight.Medium,
                                                            color = Gray900,
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                        if (isSelected) {
                                                            Icon(
                                                                imageVector = Icons.Default.CheckCircle,
                                                                contentDescription = null,
                                                                tint = Purple600,
                                                                modifier = Modifier.size(16.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Selected Symptoms Display
                            if (selectedSymptoms.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = Purple50,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Purple600)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        Text(
                                            text = "Selected Symptoms:",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Gray900
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            selectedSymptoms.forEach { symptom ->
                                                Surface(
                                                    shape = RoundedCornerShape(8.dp),
                                                    color = Purple600
                                                ) {
                                                    Row(
                                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = symptom,
                                                            fontSize = 12.sp,
                                                            color = Color.White,
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = "Remove",
                                                            tint = Color.White,
                                                            modifier = Modifier
                                                                .size(14.dp)
                                                                .clickable {
                                                                    selectedSymptoms = selectedSymptoms - symptom
                                                                }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Detailed Input Card
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

                            // Tab Row
                            TabRow(
                                selectedTabIndex = selectedTab,
                                containerColor = Gray100,
                                indicator = { tabPositions ->
                                    Box(
                                        Modifier
                                            .tabIndicatorOffset(tabPositions[selectedTab])
                                            .height(4.dp)
                                            .padding(horizontal = 16.dp)
                                            .background(Blue600, RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                    )
                                }
                            ) {
                                Tab(
                                    selected = selectedTab == 0,
                                    onClick = { selectedTab = 0 },
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Send,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Text", fontSize = 14.sp)
                                        }
                                    }
                                )
                                Tab(
                                    selected = selectedTab == 1,
                                    onClick = { selectedTab = 1 },
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Mic,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Voice", fontSize = 14.sp)
                                        }
                                    }
                                )
                                Tab(
                                    selected = selectedTab == 2,
                                    onClick = { selectedTab = 2 },
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Image,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Image", fontSize = 14.sp)
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Tab Content
                            when (selectedTab) {
                                0 -> {
                                    // Text Input
                                    Column {
                                        Text(
                                            text = "Describe your symptoms",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Gray900
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        OutlinedTextField(
                                            value = symptomText,
                                            onValueChange = { symptomText = it },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(160.dp),
                                            placeholder = {
                                                Text(
                                                    text = "Example: I've had a severe headache for 2 days. It's throbbing on the right side, gets worse with light, and I feel nauseous. I also have a mild fever around 100°F...",
                                                    fontSize = 14.sp,
                                                    color = Gray400
                                                )
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = Blue500,
                                                unfocusedBorderColor = Gray300
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Be specific about duration, severity, and location",
                                                fontSize = 12.sp,
                                                color = Gray600
                                            )
                                            Text(
                                                text = "${symptomText.length} characters",
                                                fontSize = 12.sp,
                                                color = Gray600
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        // Tips
                                        Surface(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            color = Blue50,
                                            border = androidx.compose.foundation.BorderStroke(1.dp, Blue200)
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(12.dp)
                                            ) {
                                                Text(
                                                    text = "💡 Tips for better analysis:",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Blue500
                                                )
                                                Spacer(modifier = Modifier.height(8.dp))
                                                listOf(
                                                    "Include when symptoms started",
                                                    "Describe severity (mild, moderate, severe)",
                                                    "Mention what makes it better or worse",
                                                    "Note any other related symptoms"
                                                ).forEach { tip ->
                                                    Text(
                                                        text = "• $tip",
                                                        fontSize = 12.sp,
                                                        color = Blue500
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                1 -> {
                                    // Voice Input
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                Brush.linearGradient(
                                                    colors = listOf(Purple50, Pink80)
                                                ),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .border(
                                                2.dp,
                                                Purple600,
                                                RoundedCornerShape(16.dp)
                                            )
                                            .padding(32.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isRecording) Red50 else Purple600),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Mic,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Button(
                                                onClick = {
                                                    isRecording = !isRecording
                                                    if (!isRecording) {
                                                        symptomText = "Experiencing severe headache and high fever for the past 2 days"
                                                    }
                                                },
                                                modifier = Modifier.fillMaxWidth(),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (isRecording) Red50 else Blue600
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = if (isRecording) "Stop Recording" else "Start Voice Recording",
                                                    fontSize = 16.sp
                                                )
                                            }
                                            if (isRecording) {
                                                Spacer(modifier = Modifier.height(16.dp))
                                                Text(
                                                    text = "🎤 Listening... Speak clearly about your symptoms",
                                                    fontSize = 14.sp,
                                                    color = Gray600,
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                        }
                                    }

                                    if (symptomText.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Surface(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(12.dp),
                                            color = Purple50,
                                            border = androidx.compose.foundation.BorderStroke(2.dp, Purple600)
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(16.dp)
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.CheckCircle,
                                                        contentDescription = null,
                                                        tint = Purple600,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        text = "Transcribed Text:",
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = Gray900
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                    text = symptomText,
                                                    fontSize = 14.sp,
                                                    color = Gray700
                                                )
                                            }
                                        }
                                    }
                                }
                                2 -> {
                                    // Image Upload
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                Brush.linearGradient(
                                                    colors = listOf(Green50,Blue200)
                                                ),
                                                shape = RoundedCornerShape(16.dp)
                                            )
                                            .border(
                                                2.dp,
                                                Green600,
                                                RoundedCornerShape(16.dp)
                                            )
                                            .padding(24.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                                    .background(Green600),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Image,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(16.dp))
                                            Button(
                                                onClick = { uploadedImageCount++ },
                                                modifier = Modifier.fillMaxWidth(),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Green600
                                                )
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "Upload Images",
                                                    fontSize = 16.sp
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Upload photos of visible symptoms like rashes, swelling, or injuries",
                                                fontSize = 12.sp,
                                                color = Gray600,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                    if (uploadedImageCount > 0) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "$uploadedImageCount image(s) uploaded",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Green600
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Analyze Button
                            Button(
                                onClick = {
                                    isAnalyzing = true
                                    // Simulate API call
                                    // In production, call Gemini API here
                                    kotlinx.coroutines.GlobalScope.launch {
                                        kotlinx.coroutines.delay(2500)
                                        analysisResult = AnalysisResult(
                                            seriousnessLevel = 65,
                                            seriousnessLabel = "Moderate Concern",
                                            possibleCauses = listOf(
                                                PossibleCause(
                                                    "Viral Infection (Flu)",
                                                    75,
                                                    "Most likely cause based on fever and headache combination. Typically resolves within 7-10 days with proper rest and hydration.",
                                                    "🦠"
                                                ),
                                                PossibleCause(
                                                    "Sinus Infection",
                                                    60,
                                                    "Headache with facial pressure suggests possible sinus involvement. May require antibiotics if bacterial.",
                                                    "😤"
                                                ),
                                                PossibleCause(
                                                    "Migraine",
                                                    45,
                                                    "Severe, throbbing headache pattern. Can be triggered by stress, lack of sleep, or dietary factors.",
                                                    "🧠"
                                                )
                                            ),
                                            whenToSeeDoctor = WhenToSeeDoctor(
                                                urgency = "soon",
                                                timeframe = "Within 24-48 hours",
                                                reasons = listOf(
                                                    "Fever persisting more than 3 days or above 103°F (39.4°C)",
                                                    "Severe headache unresponsive to over-the-counter medication",
                                                    "Symptoms worsening or new symptoms developing",
                                                    "Difficulty breathing, chest pain, or severe weakness",
                                                    "Persistent vomiting or inability to keep fluids down"
                                                )
                                            ),
                                            selfCareTips = listOf(
                                                SelfCareTip("Rest & Recovery", "Get 7-9 hours of sleep. Avoid strenuous activities and allow your body to heal.", "💤"),
                                                SelfCareTip("Hydration", "Drink 8-10 glasses of water daily. Add electrolyte drinks if experiencing fever or sweating.", "💧"),
                                                SelfCareTip("Pain Management", "Take acetaminophen or ibuprofen as directed. Avoid exceeding recommended dosage.", "💊"),
                                                SelfCareTip("Temperature Control", "Use cold compress on forehead. Take lukewarm bath if fever exceeds 101°F.", "🌡️"),
                                                SelfCareTip("Nutrition", "Eat light, easy-to-digest foods. Include soups, fruits, and vegetables for nutrients.", "🥗"),
                                                SelfCareTip("Monitoring", "Track temperature every 4-6 hours. Keep a symptom diary noting any changes.", "📋")
                                            )
                                        )
                                        isAnalyzing = false
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Blue600
                                ),
                                enabled = !isAnalyzing
                            ) {
                                if (isAnalyzing) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = Color.White,
                                        strokeWidth = 2.dp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Analyzing Your Symptoms...", fontSize = 16.sp)
                                } else {
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
            } else {
                // Analysis Results
                item {
                    // Analysis Complete Header
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Green50, Blue200, Blue50)
                                    )
                                )
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Analysis Complete",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Here's what we found based on your symptoms",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.9f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                // Seriousness Meter
                item {
                    val severity = analysisResult!!.seriousnessLevel
                    val (gradientColors, label, emoji) = when {
                        severity < 30 -> Triple(listOf(Green600, Green50), "Low Risk", "✅")
                        severity < 60 -> Triple(listOf(Orange500, Orange50), "Moderate", "⚠️")
                        severity < 80 -> Triple(listOf(Orange500,Red50), "Concerning", "⚠️")
                        else -> Triple(listOf(Red600, Red50), "High Risk", "🚨")
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Purple600)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Brush.linearGradient(gradientColors))
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Symptom Severity Assessment",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(24.dp))

                                // Circular Progress
                                Box(
                                    modifier = Modifier.size(160.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        progress = severity / 100f,
                                        modifier = Modifier.size(160.dp),
                                        color = Color.White,
                                        strokeWidth = 12.dp,
                                        trackColor = Color.White.copy(alpha = 0.3f)
                                    )
                                    Text(
                                        text = "${severity}%",
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = Color.White.copy(alpha = 0.2f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = "$emoji $label",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Possible Causes
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                                Column {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = null,
                                            tint = Blue600,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "Possible Causes",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Gray900
                                        )
                                    }
                                    Text(
                                        text = "Based on symptom analysis",
                                        fontSize = 14.sp,
                                        color = Gray600
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            analysisResult!!.possibleCauses.forEach { cause ->
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = Blue50.copy(alpha = 0.5f),
                                    border = androidx.compose.foundation.BorderStroke(2.dp, Blue100)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = cause.emoji,
                                            fontSize = 32.sp
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = cause.name,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Gray900
                                                )
                                                Surface(
                                                    shape = RoundedCornerShape(8.dp),
                                                    color = Blue100
                                                ) {
                                                    Text(
                                                        text = "${cause.probability}% match",
                                                        fontSize = 12.sp,
                                                        color = Blue500,
                                                        fontWeight = FontWeight.Medium,
                                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            LinearProgressIndicator(
                                                progress = cause.probability / 100f,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(8.dp)
                                                    .clip(RoundedCornerShape(4.dp)),
                                                color = Blue600,
                                                trackColor = Blue100
                                            )
                                            Spacer(modifier = Modifier.height(12.dp))
                                            Text(
                                                text = cause.description,
                                                fontSize = 14.sp,
                                                color = Gray600,
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }

                // When to See a Doctor
                item {
                    val urgency = analysisResult!!.whenToSeeDoctor.urgency
                    val (gradientColors, borderColor, badgeColor, emoji, urgencyLabel) =
                        when (urgency) {
                            "immediate" -> UrgencyUiConfig(
                                gradientColors = listOf(Red50, Red50),
                                borderColor = Red50,
                                badgeColor = Red600,
                                emoji = "🚨",
                                urgencyLabel = "Immediate Care Needed"
                            )
                            "soon" -> UrgencyUiConfig(
                                gradientColors = listOf(Orange50, Yellow50),
                                borderColor = Orange50,
                                badgeColor = Orange500,
                                emoji = "⏰",
                                urgencyLabel = "See Doctor Soon"
                            )
                            else -> UrgencyUiConfig(
                                gradientColors = listOf(Green50, Blue200),
                                borderColor = Green50,
                                badgeColor = Green600,
                                emoji = "✅",
                                urgencyLabel = "Routine Checkup"
                            )
                        }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, borderColor)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.linearGradient(gradientColors),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = emoji,
                                            fontSize = 24.sp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = "When to See a Doctor",
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Gray900
                                            )
                                            Text(
                                                text = urgencyLabel,
                                                fontSize = 14.sp,
                                                color = Gray600
                                            )
                                        }
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = badgeColor
                                    ) {
                                        Text(
                                            text = analysisResult!!.whenToSeeDoctor.timeframe,
                                            fontSize = 12.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Seek medical attention if:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Gray900
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            analysisResult!!.whenToSeeDoctor.reasons.forEach { reason ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = badgeColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = reason,
                                        fontSize = 14.sp,
                                        color = Gray700,
                                        lineHeight = 20.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }

                // Self-Care Tips
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Green600)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Green50, Blue200)
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = Green600,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Self-Care Tips",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Gray900
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            analysisResult!!.selfCareTips.forEach { tip ->
                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    color = Green50.copy(alpha = 0.3f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Green600)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = tip.emoji,
                                            fontSize = 28.sp
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = tip.category,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Gray900
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = tip.tip,
                                                fontSize = 14.sp,
                                                color = Gray600,
                                                lineHeight = 20.sp
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }

                // Action Buttons
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                analysisResult = null
                                symptomText = ""
                                selectedSymptoms = emptyList()
                                uploadedImageCount = 0
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple600
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Analyze New Symptoms", fontSize = 16.sp)
                        }

                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Back to Home", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}


//

 */
