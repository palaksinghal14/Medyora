package com.palak.medyora.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palak.medyora.Authentication.AuthRepo
import com.palak.medyora.BuildConfig
import com.palak.medyora.Repository.UserRepository
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val authRepo: AuthRepo,
    val userRepo: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Idle)
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun signOut(){

        viewModelScope.launch {
            if (BuildConfig.DEBUG) Log.d("SettingsVM", "signOut() called")
            authRepo.logout()
            if (BuildConfig.DEBUG) Log.d("SettingsVM", "logout() completed, setting SignedOut state")
            _uiState.value = SettingsUiState.SignedOut
            if (BuildConfig.DEBUG) Log.d("SettingsVM", "uiState is now: ${_uiState.value}")
        }
    }

    fun deleteData(){

        viewModelScope.launch {
            _uiState.value = SettingsUiState.Loading
            userRepo.deleteUserCompletely()
                .onSuccess {
                    _uiState.value = SettingsUiState.AccountDelete
                }
                .onFailure { e ->
                    _uiState.value = SettingsUiState.Error(e.toAppException())
                }

        }
    }
}


sealed class SettingsUiState {
    object Idle : SettingsUiState()
    object Loading : SettingsUiState()
    object SignedOut : SettingsUiState()
    object AccountDelete : SettingsUiState()
    data class Error(val exception: AppException) : SettingsUiState()
}