package com.example.medyora.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medyora.Authentication.AuthRepo
import com.example.medyora.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val authRepo: AuthRepo,
    val userRepo: UserRepository
): ViewModel() {

    fun signOut(){

        viewModelScope.launch {
            authRepo.logout()
        }
    }

    fun deleteData(){

        viewModelScope.launch {
            try {
                userRepo.deleteUserCompletely()
            } catch (e: Exception) {
                // log or handle later
            }
        }
    }
}