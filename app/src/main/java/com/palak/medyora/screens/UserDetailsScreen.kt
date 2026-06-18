package com.palak.medyora.screens

import android.R
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.model.UserProfile
import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.ui.components.MedyoraCard
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue500
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray200
import com.palak.medyora.ui.theme.Gray300
import com.palak.medyora.ui.theme.Gray400
import com.palak.medyora.ui.theme.Gray500
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.ui.theme.Gray800
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.White
import com.palak.medyora.viewmodels.UserProfileState
import com.palak.medyora.viewmodels.UserViewModel

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

    fun isPersonalDetailsValid():Boolean{
        return name.isNotBlank() &&
                age.toIntOrNull()?.let{it>0} == true &&
                dob.isNotBlank() &&
                gender.isNotBlank() &&
                contact.isNotBlank()

    }

    fun isPhysicalDetailsValid():Boolean{
        return activityLevel.isNotBlank()

    }

    fun isMedicalDetailsValid():Boolean{
        return selectedConditions.isNotEmpty() || extraConditions.isNotEmpty()

    }

    //to show error when user tries to click next or submit without filling necessary details
    var showErrors by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue50)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Brand mark
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Blue600, Color(0xFF5B7BF0))
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "M",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Complete your profile",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray900,

                    )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Help us personalize your experience",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray500,
                )
                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = progress,
                    modifier= Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                    ,
                    color= Blue600,
                    trackColor = Blue100
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Step label
                Text(
                    text = "Step $currentSection of 3",
                    fontSize = 12.sp,
                    color = Gray500,

                )

            }


            Spacer(modifier = Modifier.height(12.dp))

            // Section card
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
                }

            }

            // to make sure that user enter all the data before moving on to next step
            val isCurrentStepValid= when(currentSection){
                 1-> isPersonalDetailsValid()
                2-> isPhysicalDetailsValid()
                3->isMedicalDetailsValid()
                else -> false
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        if (currentSection > 1)
                         currentSection--
                    },
                    enabled = if(currentSection==1) false else true,
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.5.dp, if (currentSection > 1) Blue600 else Gray300),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(text = "Back",
                        color = if (currentSection > 1) White else White)
                }

                //check if all the fields are filled and then only will move to next section
                Button(
                    onClick = {
                        if(currentSection!=3) currentSection++ else{
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

                            userViewModel.SaveUserProfile(userProfile) {
                                OnNavToMainPage()
                            }
                        }
                    },
                    enabled =isCurrentStepValid,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600,
                        disabledContainerColor = Blue100)
                ) {
                    Text(text = if(currentSection!=3) "Next" else "Get Started",
                        fontWeight = FontWeight.SemiBold,
                        color = White)
                }
            }
        }
    }
  when(state){
      is UserProfileState.Error -> {
          FullScreenError(
              exception = state.msg,
              onRetry = null
          )
      }
      is UserProfileState.Loading -> {
          CircularProgressIndicator()
      }

      UserProfileState.Idle -> {}
      UserProfileState.Success -> {}
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

// Shared field colors — define once, use everywhere
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Blue600,
        unfocusedBorderColor = Gray200,
        focusedLabelColor = Blue600,
        unfocusedLabelColor = Gray500,
        cursorColor = Blue600,
        focusedTextColor = Gray900,
        unfocusedTextColor = Gray900,
        focusedContainerColor = White,
        unfocusedContainerColor = White
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp,Blue100),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(14.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "",
                    tint = Blue600,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Personal Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue600
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = NameOnChange,
                label = {
                    Text(
                        text = "Name *")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = AgeOnChange,
                label = {
                    Text(
                        text = "Age  * "   )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number , imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors

            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dob,
                onValueChange = DobOnChange,
                label =
                    {
                        Text(
                            text = "Date of Birth (dd-mm-yy) *"
                        )
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contact,
                onValueChange = ContactOnChange,
                label = {
                    Text(
                        text = "Contact Details  *"
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number , imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = fieldColors
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
                        Text(text = "Gender  *")
                    },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors

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
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = Blue600,
                                disabledTextColor = Gray500
                            )
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

        val fieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Blue600,
            unfocusedBorderColor = Gray200,
            focusedLabelColor = Blue600,
            unfocusedLabelColor = Gray500,
            cursorColor = Blue600,
            focusedTextColor = Gray900,
            unfocusedTextColor = Gray900,
            focusedContainerColor = White,
            unfocusedContainerColor = White
        )

        Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = White),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            border = BorderStroke(1.dp, Blue100)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
           ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
                ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "",
                    tint = Blue600,
                    modifier = Modifier.size(20.dp)
                )
                Text(text = "Physical Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue600)
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = height,
                onValueChange = onHeightChange,
                label = { Text("Height (cm)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors =fieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                label = { Text("Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors =fieldColors
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = activityExpanded,
                onExpandedChange = { activityExpanded = !activityExpanded }
            ){
                OutlinedTextField(
                    value = activityLevel,
                    onValueChange = onActivityLevelChange,
                    label = { Text("Lifestyle  *") },
                    shape = RoundedCornerShape(12.dp),
                    colors =fieldColors,
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
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = Blue600,
                                disabledTextColor = Gray500
                            )
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
    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Blue600,
        unfocusedBorderColor = Gray200,
        focusedLabelColor = Blue600,
        unfocusedLabelColor = Gray500,
        cursorColor = Blue600,
        focusedTextColor = Gray900,
        unfocusedTextColor = Gray900,
        focusedContainerColor = White,
        unfocusedContainerColor = White
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor =  White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        border = BorderStroke(1.dp, Blue100)
    )
     {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = "",
                    tint = Blue600,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Medical Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue600
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Select your medical conditions:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray800
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
                            modifier = Modifier.padding(4.dp),
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
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = extraCondition,
                        onValueChange = onExtraConditionChange,
                        label = { Text("Add other condition") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = fieldColors
                    )
                    IconButton(onClick = onAddExtraCondition) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add condition",
                            tint = Blue600
                        )
                    }
                }

                if (extraConditions.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "OTHER CONDITIONS:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue600)
                    Spacer(modifier = Modifier.height(4.dp))
                    Column {
                        extraConditions.forEach { condition ->
                            Text(
                                text = "• $condition",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 12.sp,
                                color = Blue600)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
