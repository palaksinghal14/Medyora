package com.palak.medyora.screens

import android.Manifest
import androidx.compose.foundation.layout.WindowInsets
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.model.NearbyDoctors.NearbyDoctor
import com.palak.medyora.viewmodels.NearbyDoctorsUiState
import com.palak.medyora.viewmodels.NearbyDoctorsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TopAppBarDefaults.windowInsets

import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.utils.AppException


// presentation/nearbydoctors/NearbyDoctorsScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyDoctorsScreen(
    viewModel: NearbyDoctorsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) viewModel.onPermissionGranted()
        else viewModel.onPermissionDenied()
    }

    // Request permission on first launch
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Scaffold(
        modifier = Modifier.statusBarsPadding(),
                topBar = {
            TopAppBar(
                title = { Text("Nearby Doctors") },
                windowInsets = WindowInsets(0)
            )


        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is NearbyDoctorsUiState.Idle -> Unit

                is NearbyDoctorsUiState.Loading ->
                    CircularProgressIndicator()

                is NearbyDoctorsUiState.Success ->
                    DoctorList(doctors = state.doctors)

                is NearbyDoctorsUiState.Error ->
                    FullScreenError(
                        exception = state.message,
                        onRetry = {viewModel.retry()}
                    )

                is NearbyDoctorsUiState.PermissionDenied ->
                   FullScreenError(
                       exception = AppException.LocationPermissionDeniedException,
                       onRetry = null // no retry makes sense here, user needs to go to settings
                   )
            }
        }
    }
}

@Composable
private fun DoctorList(doctors: List<NearbyDoctor>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(doctors, key = { it.placeId }) { doctor ->
            DoctorCard(doctor = doctor)
        }
    }
}

@Composable
private fun DoctorCard(doctor: NearbyDoctor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = doctor.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = doctor.address,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rating
                doctor.rating?.let { rating ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = rating.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Open/Closed badge
                doctor.isOpen?.let { isOpen ->
                    Text(
                        text = if (isOpen) "Open" else "Closed",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isOpen) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }
        }
    }
}
