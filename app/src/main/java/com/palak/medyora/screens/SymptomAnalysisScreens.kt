
package com.palak.medyora.screens


import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.model.SymptomAnalysis.RiskLevel
import com.palak.medyora.model.SymptomAnalysis.SymptomDuration
import com.palak.medyora.model.SymptomAnalysis.SymptomSeverity
import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.ui.components.InlineError
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray200
import com.palak.medyora.ui.theme.Gray300
import com.palak.medyora.ui.theme.Gray400
import com.palak.medyora.ui.theme.Gray500
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.Green100
import com.palak.medyora.ui.theme.Green50
import com.palak.medyora.ui.theme.Green600
import com.palak.medyora.ui.theme.Orange500
import com.palak.medyora.ui.theme.Purple600
import com.palak.medyora.ui.theme.Red100
import com.palak.medyora.ui.theme.Red50
import com.palak.medyora.ui.theme.Red600
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
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        when(flowState){
            is SymptomFlowState.Idle -> SymptomInputScreen(uiState,symptomViewModel)
            is SymptomFlowState.Loading -> CircularProgressIndicator()
            is SymptomFlowState.Error -> {
                FullScreenError(
                    exception =(flowState as SymptomFlowState.Error).message ,
                    onRetry = {symptomViewModel.resetSymptomFlow()}
                )
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
        modifier = Modifier.statusBarsPadding(),
        contentWindowInsets = WindowInsets(0),
                topBar = {
            Surface(
                shadowElevation = 4.dp,
                color = White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
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
                    border = BorderStroke(2.dp, Blue100)
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
                                    .background(Blue50),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = Blue600,
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
                    border =BorderStroke(2.dp, Blue100)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Blue50)
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
                                    color = Blue600
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            border = BorderStroke(2.dp, Blue100)
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
                                    ),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    )
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
                                            .fillMaxWidth(),

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
                                        ),
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
                                                },
                                                colors = MenuDefaults.itemColors(
                                                    textColor = Blue600,
                                                    disabledTextColor = Gray500
                                                )
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
                                            .fillMaxWidth(),

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
                                        ),
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
                                                },
                                                colors = MenuDefaults.itemColors(
                                                    textColor = Blue600,
                                                    disabledTextColor = Gray500
                                                )
                                            )

                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // show error if user clicks analyze button before filling the details
                                uiState.errorMessage?.let { exception ->
                                    InlineError(exception = exception)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                // button
                                Button(
                                    onClick = {
                                        viewModel.analyzeSymptom()
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                                ) {

                                    Row(horizontalArrangement = Arrangement.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Psychology,
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Analyze Symptoms", fontSize = 16.sp, color = White)
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
                    color = White,
                    border = BorderStroke(1.dp, Red100)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Red600,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Medical Disclaimer",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Red600
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "This analysis is for informational purposes only and does not replace professional medical advice. Always consult with a healthcare provider for accurate diagnosis and treatment.",
                                fontSize = 12.sp,
                                color = Red600,
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
        modifier = Modifier.statusBarsPadding(),
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Surface(
                shadowElevation = 4.dp,
                color = White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
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
                                text = "Symptom Analysis Result",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gray900
                            )
                            Text(
                                text = "Your Personalised Health Insights",
                                fontSize = 14.sp,
                                color = Gray600
                            )
                        }
                    }

                }
            }
        },
        containerColor = Color.Transparent
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            // ✅ SUCCESS CARD
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Green50
                    ),
                    border = BorderStroke(1.dp, Green100),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Green600,
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Analysis Complete",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Green600
                        )

                        Text(
                            "Here's what we found based on your symptoms",
                            fontSize = 14.sp,
                            color = Gray600,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // ✅ RISK LEVEL CARD
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    border = BorderStroke(1.dp, Blue100)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            "Severity Assessment",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Gray700
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            flowState.riskLevel.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (flowState.riskLevel) {
                                RiskLevel.LOW -> Green600
                                RiskLevel.MODERATE -> Color(0xFFF59E0B)
                                RiskLevel.HIGH -> Red600
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


                        // Header
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Blue50, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Possible Causes",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Gray900
                                )
                                Text(
                                    text = "Based on your symptoms",
                                    fontSize = 12.sp,
                                    color = Gray500
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        //  Each cause as separate card
                        flowState.causes.forEach { cause ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(10.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                border = BorderStroke(1.dp, Blue50)
                            ) {
                                Text(
                                    text = cause,
                                    modifier = Modifier.padding(12.dp),
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
                    colors = CardDefaults.cardColors(containerColor = White),
                    border = BorderStroke(1.dp, Blue100)
                ) {

                    Column(modifier = Modifier.padding(16.dp))
                    {

                        //  Highlighted Header
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE8F5E9)
                            )
                        )
                        {
                            Column(modifier = Modifier.padding(12.dp))
                            {

                                Row(verticalAlignment = Alignment.CenterVertically)
                                {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .background(Green50, RoundedCornerShape(10.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = Green600,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = "Recommendations",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Gray900
                                        )
                                        Text(
                                            text = "Self-care suggestions",
                                            fontSize = 12.sp,
                                            color = Gray500
                                        )
                                    }
                                }


                                Spacer(modifier = Modifier.height(12.dp))

                                //  Each recommendation as card
                                flowState.recommendation.forEach { rec ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        elevation = CardDefaults.cardElevation(4.dp),
                                        border = BorderStroke(1.dp, Green100),
                                        colors = CardDefaults.cardColors(containerColor = Green50),
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(12.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                tint = Green600,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
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

                }
            }

            // Medical Disclaimer
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    border = BorderStroke(1.dp, Red100)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Red600,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Medical Disclaimer",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Red600

                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "This analysis is for informational purposes only and does not replace professional medical advice. Always consult with a healthcare provider for accurate diagnosis and treatment.",
                                fontSize = 12.sp,
                                color = Red600,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // ✅ BUTTON
            item {
                Button(
                    onClick = { viewModel.resetSymptomFlow() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(
                        "Analyze Another Symptom",
                        fontWeight = FontWeight.SemiBold,
                        color = White
                    )
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
            modifier = Modifier.statusBarsPadding(),
            contentWindowInsets = WindowInsets(0),
            containerColor = Blue50
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .statusBarsPadding()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(8.dp),
                    border = BorderStroke(1.dp, Blue100)

                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
// Header
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Blue50, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = Blue600,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Quick Follow-Up",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Gray900
                                )
                                Text(
                                    text = "One more question to refine results",
                                    fontSize = 12.sp,
                                    color = Gray500
                                )
                            }
                        }

                        //question
                        Text(
                            text = flowState.question,
                            fontSize = 16.sp,
                            color = Gray700,
                            lineHeight = 22.sp
                        )

                        //options
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
                                    containerColor = if (isSelected) Blue600 else Blue50
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) Blue600 else Blue100
                                )
                            ) {
                                Text(
                                    text = option,
                                    modifier = Modifier.padding(14.dp),
                                    fontSize = 14.sp,
                                    color = if (isSelected) White else Gray700,
                                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
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
                                containerColor = if (selectedOption != null) Blue600 else Blue100
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                "Continue",
                                fontWeight = FontWeight.SemiBold,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    }



