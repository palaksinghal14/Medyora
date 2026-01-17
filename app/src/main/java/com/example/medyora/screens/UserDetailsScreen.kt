package com.example.medyora.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medyora.model.UserProfile
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue50
import com.example.medyora.ui.theme.Blue500
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray100
import com.example.medyora.ui.theme.Gray200
import com.example.medyora.ui.theme.Gray500
import com.example.medyora.ui.theme.Gray700
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.viewmodels.UserProfileState
import com.example.medyora.viewmodels.UserViewModel

@Composable
fun UserDetailsScreen(
    OnNavToMainPage:() ->Unit
){

    val userViewModel: UserViewModel= hiltViewModel()
    val state =userViewModel.state.value

    var currentSection by remember { mutableStateOf(1) }

    // Personal Details states
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    // Physical Details states
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }
    var BloodType by remember { mutableStateOf("") }
    var allergies by remember { mutableStateOf("") }


    // Medical History states
    val conditions = listOf(
        "Diabetes",
        "Low BP",
        "High BP",
        "Anxiety",
        "Indigestion",
        "Acidity",
        "Hypertension",
        "Thyroid",
        "High Cholesterol",
        "Asthama",
        "Depression",
        "Obesity"
    )

    val selectedConditions = remember { mutableStateListOf<String>() }
    var extraCondition by remember { mutableStateOf("") }
    val extraConditions = remember { mutableStateListOf<String>() }
    val progress= when(currentSection){
         1-> 0.33f
         2-> 0.66f
         3-> 1f
        else -> 0f
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
    ){
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Complete your profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue600,

                    )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Help us personalize your experience",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue500,
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier= Modifier
                    .fillMaxWidth(),
                color= Gray500,
                trackColor = Blue600
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .weight(1f)           // take remaining space between header and buttons
                    .fillMaxWidth()
            )
            {

                when(currentSection){
                    1-> PersonalDetailsCard(
                        name = name,
                        NameOnChange = { name = it },
                        age = age,
                        AgeOnChange = { age = it },
                        dob = dob,
                        DobOnChange = { dob = it },
                        gender = gender,
                        GenderOnChange = { gender = it },
                        contact = contact,
                        ContactOnChange = { contact = it }
                    )
                    2-> PhysicalDetailsCard(
                        height = height,
                        onHeightChange = { height = it },
                        weight = weight,
                        onWeightChange = { weight = it },
                        activityLevel = activityLevel,
                        onActivityLevelChange = { activityLevel = it }
                    )
                    3-> MedicalHistoryCard(
                        conditions = conditions,
                        selectedConditions = selectedConditions,
                        extraCondition = extraCondition,
                        onExtraConditionChange = {extraCondition=it},
                        extraConditions = extraConditions,
                        onAddExtraCondition = {
                            if (extraCondition.isNotBlank()) {
                                extraConditions.add(extraCondition.trim())
                                extraCondition = ""
                            }
                        }
                    )
                    else -> null
                }

            }



            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                         currentSection--
                    },
                    enabled = if(currentSection==1) false else true,
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(text = "Back")
                }

                Button(
                    onClick = {

                         val userProfile= UserProfile(
                             name=name,
                             age = age.toIntOrNull() ?: 0,
                             dob = dob,
                             gender = gender,
                             contact = contact,
                             heightCm = height.toIntOrNull() ?: 0,
                             weightKg = weight.toIntOrNull() ?: 0,
                             activityLevel = activityLevel,
                             conditions = selectedConditions + extraConditions
                         )
                         if(currentSection!=3) currentSection++ else
                        userViewModel.SaveUserProfile(userProfile) {
                            OnNavToMainPage()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(text = if(currentSection!=3) "Next" else "Get Started")
                }
            }
        }
    }
  when(state){
      is UserProfileState.Error -> {
          Text(
              text = "something wrong",
              color = Color.Red
          )
      }
      is UserProfileState.Loading -> {
          CircularProgressIndicator()
      }

      else ->{

      }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDetailsCard(
  name:String,
  NameOnChange:(String)->Unit,
  age: String,
  AgeOnChange: (String) -> Unit,
  dob: String,
  DobOnChange: (String) -> Unit,
  gender: String,
  GenderOnChange: (String) -> Unit,
  contact: String,
  ContactOnChange: (String) -> Unit
) {
    val genderOptions = listOf("Female", "Male", "Others", "Prefer not to say")
    var genderExpanded by remember { mutableStateOf(false) }



    Card(
        modifier = Modifier
            .padding(14.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Blue200)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    tint = Blue500
                )
                Text(
                    text = "Personal Details",
                    color = Blue500
                )
            }
            OutlinedTextField(
                value = name,
                onValueChange = NameOnChange,
                label = {
                    Text(
                        text = "Enter Name",
                        color = Gray700
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = AgeOnChange,
                label = {
                    Text(
                        text = "Enter Age",
                        color = Gray700
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dob,
                onValueChange = DobOnChange,
                label =
                    {
                        Text(
                            text = "DOB:dd-mm-yy",
                            color = Gray700
                        )
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contact,
                onValueChange = ContactOnChange,
                label = {
                    Text(
                        text = "Contact Details",
                        color = Gray700
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = { genderExpanded = !genderExpanded }
            ) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = GenderOnChange,
                    label = {
                        Text(
                            text = "Gender",
                            color = Gray700
                        )
                    },
                    readOnly = true,

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Blue600,
                        unfocusedBorderColor = Blue600,
                        focusedLabelColor = Gray700,
                        cursorColor = Blue100,
                        focusedTextColor = Blue500
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                GenderOnChange(option)
                                genderExpanded = false
                            }
                        )
                    }
                }

            }
        }
    }


}

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
fun PhysicalDetailsCard(
    height: String,
    onHeightChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    activityLevel: String,
    onActivityLevelChange: (String) -> Unit
) {
        val activityOptions = listOf(
            "Sedentary (little or no exercise)",
            "Lightly active (1–3 days/week)",
            "Moderately active (3–5 days/week)",
            "Very active (6–7 days/week)",
            "Super active (intense exercise/physical job)"
        )

        var activityExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Blue200)
    ) {
        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier
                .padding(8.dp)) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "",
                    tint = Blue500
                )
                Text(text = "Physical Details",
                    color= Blue500)
            }
            OutlinedTextField(
                value = height,
                onValueChange = onHeightChange,
                label = { Text("Height (cm)",
                    color= Gray700) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                label = { Text("Weight (kg)",
                    color= Gray700) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = activityExpanded,
                onExpandedChange = { activityExpanded = !activityExpanded }
            ){
                OutlinedTextField(
                    value = activityLevel,
                    onValueChange = onActivityLevelChange,
                    label = { Text("Active or Inactive ",
                        color= Gray700) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Blue600,
                        unfocusedBorderColor = Blue600,
                        focusedLabelColor = Gray700,
                        cursorColor = Blue100,
                        focusedTextColor = Blue500
                    ),
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = activityExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = activityExpanded,
                    onDismissRequest = { activityExpanded = false }
                ) {
                    activityOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onActivityLevelChange(option)
                                activityExpanded = false
                            }
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MedicalHistoryCard(
    conditions: List<String>,
    selectedConditions: MutableList<String>,
    extraCondition: String,
    onExtraConditionChange: (String) -> Unit,
    extraConditions: List<String>,
    onAddExtraCondition: () -> Unit,

) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Blue200)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "",
                    tint = Blue500
                )
                Text(
                    text = "Medical Details",
                    color = Blue500
                )
            }


            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Select your medical conditions:",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    maxItemsInEachRow = 3
                ) {
                    conditions.forEach { condition ->
                        FilterChip(
                            selected = selectedConditions.contains(condition),
                            onClick = {
                                // if it is already in thd selected list , and user clicks so will remove it else add it
                                if (selectedConditions.contains(condition)) {
                                    selectedConditions.remove(condition)
                                } else {
                                    selectedConditions.add(condition)
                                }
                            },
                            label = { Text(condition) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Blue600,
                                selectedLabelColor = Color.White,
                                containerColor = Blue50,
                                labelColor = Gray900,
                                iconColor = Blue600,
                                selectedLeadingIconColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Extra condition input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = extraCondition,
                        onValueChange = onExtraConditionChange,
                        label = { Text("Add other condition") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onAddExtraCondition) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add condition"
                        )
                    }
                }

                if (extraConditions.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Other conditions:")
                    Spacer(modifier = Modifier.height(4.dp))
                    Column {
                        extraConditions.forEach { condition ->
                            Text(text = "• $condition")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
