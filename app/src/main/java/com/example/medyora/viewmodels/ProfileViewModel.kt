package com.example.medyora.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medyora.Repository.UserRepository
import com.example.medyora.model.UserProfile
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    val userRepo : UserRepository
) : ViewModel(){

    private val _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileState: StateFlow<ProfileUiState> = _profileState

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            try {
                val profile = userRepo.getUserProfile()
                if(profile!=null){
                    _profileState.value = ProfileUiState.Success(profile)
                }
                else{
                    _profileState.value =ProfileUiState.Error("Profile doesn't exist")
                }
            } catch (e: Exception) {
                _profileState.value = ProfileUiState.Error("Failed to load profile")
            }
        }
    }

    fun updateProfile(
        updatedProfile: UserProfile,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                userRepo.updateUserProfile(updatedProfile)
                _profileState.value = ProfileUiState.Success(updatedProfile)
                onSuccess()    // means we will perform a function after updating profile whereever updateprofile function is called
            } catch (e: Exception) {
                _profileState.value = ProfileUiState.Error("Failed to update profile")
            }
        }
    }

}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: UserProfile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
