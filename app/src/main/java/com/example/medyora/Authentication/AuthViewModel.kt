package com.example.medyora.Authentication

import android.R
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val authRepo: AuthRepo
) : ViewModel(){

   private val _state =mutableStateOf<AuthState>(AuthState.Idle)
    val state: State<AuthState> =  _state

     fun SignUp(email :String , password : String) {
         viewModelScope.launch {
             val user = authRepo.SignUp(email, password)
             if (user != null) {
                 _state.value = AuthState.Success(user)
             } else {
                 _state.value = AuthState.error("Not valid")
             }
         }
     }
         fun SignIn(email :String, password : String){
             viewModelScope.launch {
                 _state.value= AuthState.Loading
                 val user = authRepo.SignIn(email,password)
                 if (user != null){
                     _state.value= AuthState.Success(user)
                 }
                 else{
                     _state.value= AuthState.error("Not valid")
                 }
             }
             }

    sealed class AuthState(){
        object  Idle : AuthState()
        object Loading: AuthState()
        data class Success(val user: FirebaseUser): AuthState()
        data class error(val message: String) : AuthState()
    }
}

