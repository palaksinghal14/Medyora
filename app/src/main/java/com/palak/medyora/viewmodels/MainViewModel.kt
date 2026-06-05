package com.palak.medyora.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palak.medyora.Repository.UserRepository
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
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
            userRepository.getUserProfile()
                .onSuccess { profile ->
                    if (profile != null) {
                        _uiState.value = MainUiState.Success(profile.name)
                    } else {
                        _uiState.value = MainUiState.Error(AppException.UserNotFoundException)
                    }
                }
                .onFailure { e->
                    _uiState.value = MainUiState.Error(e.toAppException())
                }
        }
}
}

sealed class MainUiState {
    object Loading : MainUiState()
    data class Success(val userName: String) : MainUiState()
    data class Error(val message: AppException) : MainUiState()
}
