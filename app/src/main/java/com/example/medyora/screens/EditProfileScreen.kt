package com.example.medyora.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medyora.model.UserProfile
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray700
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.ui.theme.Red600
import com.example.medyora.ui.theme.White
import com.example.medyora.viewmodels.ProfileUiState
import com.example.medyora.viewmodels.ProfileViewModel


@Composable
fun EditProfileRoute(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()

    when (val state = profileState) {
        is ProfileUiState.Success -> {
            EditProfileScreen(
                profile = state.profile,
                onSave = { updatedProfile ->
                    viewModel.updateProfile(updatedProfile) {   // the action we are performing here is calling viewmodel update function and passing the profile as input bthat we have received
                        onBack()   // go back after save
                    }
                }
            )
        }

        is ProfileUiState.Loading -> { Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        }

        is ProfileUiState.Error -> {   Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Failed to load profile")
        }
        }
    }
}


@Composable
fun EditProfileScreen(
    profile: UserProfile,
    onSave: (UserProfile) ->  Unit
){
    // Personal
    var name by remember { mutableStateOf(profile.name) }
    var age by remember { mutableStateOf(profile.age.toString()) }
    var dob by remember { mutableStateOf(profile.dob) }
    var gender by remember { mutableStateOf(profile.gender) }
    var contact by remember { mutableStateOf(profile.contact) }

    // Physical
    var height by remember { mutableStateOf(profile.heightCm.toString()) }
    var weight by remember { mutableStateOf(profile.weightKg.toString()) }
    var activityLevel by remember { mutableStateOf(profile.activityLevel) }

    // Medical
    val selectedConditions = remember { mutableStateListOf<String>().apply {
        addAll(profile.conditions)
    }}

    var showError by remember { mutableStateOf(false) }

    fun isFormValid(): Boolean {
        return name.isNotBlank() &&
                age.toIntOrNull()?.let { it > 0 } == true &&
                gender.isNotBlank() &&
                contact.isNotBlank() &&
                activityLevel.isNotBlank()
    }

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
                    text = "Edit Profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gray900
                )
                Text(
                    text = "Update your profile information",
                    fontSize = 16.sp,
                    color = Gray600
                )
            }
        }


        // Personal Details
        item {
            EditPersonalDetailsCard(
                name = name,
                onNameChange = { name = it },
                age = age,
                onAgeChange = { age = it },
                dob = dob,
                onDobChange = { dob = it },
                gender = gender,
                onGenderChange = { gender = it },
                contact = contact,
                onContactChange = { contact = it }
            )
        }

        // Physical Details
        item {
            EditPhysicalDetailsCard(
                height = height,
                onHeightChange = { height = it },
                weight = weight,
                onWeightChange = { weight = it },
                activityLevel = activityLevel,
                onActivityLevelChange = { activityLevel = it }
            )
        }

        // Medical Details
        item {
            EditMedicalDetailsCard(
                selectedConditions = selectedConditions
            )
        }

        // Error message
        if (showError) {
            item {
                Text(
                    text = "Please fill all mandatory fields",
                    color = Red600,
                    fontSize = 14.sp
                )
            }
        }

        // Save Button
        item {
            Button(
                onClick = {
                    if (!isFormValid()) {
                        showError = true
                        return@Button
                    }

                    val updatedProfile = profile.copy(
                        name = name,
                        age = age.toIntOrNull() ?: profile.age,
                        dob = dob,
                        gender = gender,
                        contact = contact,
                        heightCm = height.toIntOrNull() ?: 0,
                        weightKg = weight.toIntOrNull() ?: 0,
                        activityLevel = activityLevel,
                        conditions = selectedConditions
                    )
                    onSave(updatedProfile)  // will perform some action and give updated profile
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)
            ) {
                Text(text = "Save Changes", fontSize = 16.sp)
            }
        }
    }

}


@Composable
fun EditPersonalDetailsCard(
    name: String,
    onNameChange :(String)-> Unit,
    age : String,
    onAgeChange :(String)-> Unit,
    dob : String,
    onDobChange :(String)-> Unit,
    gender: String,
    onGenderChange :(String)-> Unit,
    contact : String,
    onContactChange :(String)-> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Gray900)
    ){
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Gray700
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Personal Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                )
            )
            OutlinedTextField(
                value = age,
                onValueChange = onAgeChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                value = dob,
                onValueChange = onDobChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                value = gender,
                onValueChange = onGenderChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                )
            )
            OutlinedTextField(
                value = contact,
                onValueChange = onContactChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }

    }
}


@Composable
fun EditPhysicalDetailsCard(
    height : String,
    onHeightChange :(String)-> Unit,
    weight : String,
    onWeightChange :(String)-> Unit,
    activityLevel : String,
    onActivityLevelChange:(String)-> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Gray900)
    ){
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Psychology,
                    contentDescription = null,
                    tint = Gray700
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Physical Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = height,
                onValueChange = onHeightChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                )
            )
            OutlinedTextField(
                value = weight,
                onValueChange = onWeightChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                )
            )
            OutlinedTextField(
                value = activityLevel,
                onValueChange = onActivityLevelChange,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = White,
                    unfocusedContainerColor= White,
                    focusedTextColor= Gray900,
                    unfocusedTextColor=Gray900
                )
            )

        }

    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditMedicalDetailsCard(
    selectedConditions: MutableList<String>
) {
    val allConditions = listOf(
        "Diabetes", "Low BP", "High BP", "Anxiety", "Thyroid",
        "Asthma", "Depression", "Obesity"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, Gray900)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = null,
                    tint = Gray700
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Medical Conditions",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                allConditions.forEach { condition ->
                    FilterChip(
                        selected = selectedConditions.contains(condition),
                        onClick = {
                            if (selectedConditions.contains(condition)) {
                                selectedConditions.remove(condition)
                            } else {
                                selectedConditions.add(condition)
                            }
                        },
                        label = { Text(condition) }
                    )
                }
            }
        }
    }
}


