package com.palak.medyora.Authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
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
             _state.value= AuthState.Loading
             try {
                 val user = authRepo.SignUp(email, password)
                 if (user != null) {
                     _state.value = AuthState.Success(user)
                 } else {
                     _state.value = AuthState.error(AppException.UnknownException("Sign up failed. Please try again."))
                 }
             }catch (e: Exception){
                 _state.value= AuthState.error(e.toAppException())
             }

         }
     }
         fun SignIn(email :String, password : String){
             viewModelScope.launch {
                 _state.value= AuthState.Loading
                 try{

                     val user = authRepo.SignIn(email,password)
                     if (user != null){
                         _state.value= AuthState.Success(user)
                     } else{
                         _state.value= AuthState.error(AppException.UnknownException("Sign in failed. Please try again."))
                     }

                 }catch(e: Exception){
                   _state.value= AuthState.error(e.toAppException())
                 }
             }
         }

    fun resetState() {
        _state.value = AuthState.Idle
    }

    sealed class AuthState(){
        object  Idle : AuthState()
        object Loading: AuthState()
        data class Success(val user: FirebaseUser): AuthState()
        data class error(val message: AppException) : AuthState()
    }
}

