package com.example.medyora.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medyora.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel() {
    private val _uiState = mutableStateOf<MainUiState>(MainUiState.Loading)
    val uiState : State<MainUiState>  =  _uiState

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                val profile = userRepository.getUserProfile()
                if (profile != null) {
                    _uiState.value = MainUiState.Success(profile.name)
                } else {
                    _uiState.value = MainUiState.Error("User profile not found")
                }
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error("Something went wrong")
            }
        }
}
}

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val userName: String) : MainUiState()
    data class Error(val message: String) : MainUiState()
}
