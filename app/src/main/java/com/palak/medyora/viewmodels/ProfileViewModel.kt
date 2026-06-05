package com.palak.medyora.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palak.medyora.Repository.UserRepository
import com.palak.medyora.model.UserProfile
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    val userRepo : UserRepository
) : ViewModel(){

    private val _profileState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val profileState: StateFlow<ProfileUiState> = _profileState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _profileState.value = ProfileUiState.Loading
            userRepo.getUserProfile()
                .onSuccess { profile ->
                    if(profile!=null){
                        _profileState.value = ProfileUiState.Success(profile)
                    }
                    else{
                        _profileState.value =ProfileUiState.Error(AppException.UserNotFoundException)
                    }
                }
                .onFailure { e->
                    _profileState.value = ProfileUiState.Error(e.toAppException())
                }
        }
    }

    fun updateProfile(
        updatedProfile: UserProfile,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            userRepo.updateUserProfile(updatedProfile)
                .onSuccess {
                    _profileState.value = ProfileUiState.Success(updatedProfile)
                    onSuccess()   // means we will perform a function after updating profile whereever updateprofile function is called
                }
                .onFailure { e ->
                    _profileState.value = ProfileUiState.Error(e.toAppException())
                }
        }
    }

}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: UserProfile) : ProfileUiState()
    data class Error(val message: AppException) : ProfileUiState()
}
