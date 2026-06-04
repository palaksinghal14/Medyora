package com.palak.medyora.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palak.medyora.Repository.DoctorsRepository
import com.palak.medyora.model.NearbyDoctors.NearbyDoctor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// presentation/nearbydoctors/NearbyDoctorsViewModel.kt

sealed class NearbyDoctorsUiState {
    object Idle : NearbyDoctorsUiState()
    object Loading : NearbyDoctorsUiState()
    data class Success(val doctors: List<NearbyDoctor>) : NearbyDoctorsUiState()
    data class Error(val message: String) : NearbyDoctorsUiState()
    object PermissionDenied : NearbyDoctorsUiState()
}

@HiltViewModel
class NearbyDoctorsViewModel @Inject constructor(
    private val repository: DoctorsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<NearbyDoctorsUiState>(NearbyDoctorsUiState.Idle)
    val uiState: StateFlow<NearbyDoctorsUiState> = _uiState.asStateFlow()

    fun onPermissionGranted() {
        fetchNearbyDoctors()
    }

    fun onPermissionDenied() {
        _uiState.value = NearbyDoctorsUiState.PermissionDenied
    }

    private fun fetchNearbyDoctors() {
        viewModelScope.launch {
            _uiState.value = NearbyDoctorsUiState.Loading

            repository.getNearbyDoctors()
                .onSuccess { doctors ->
                    _uiState.value = if (doctors.isEmpty())
                        NearbyDoctorsUiState.Error("No doctors found nearby")
                    else
                        NearbyDoctorsUiState.Success(doctors)
                }
                .onFailure { error ->
                    _uiState.value = NearbyDoctorsUiState.Error(
                        error.message ?: "Something went wrong"
                    )
                }
        }
    }

    fun retry() = fetchNearbyDoctors()
}