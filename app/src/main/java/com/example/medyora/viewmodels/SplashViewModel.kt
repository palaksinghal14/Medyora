package com.example.medyora.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medyora.Authentication.AuthRepo
import com.example.medyora.Repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val authRepo: AuthRepo,
    val userRepository: UserRepository
): ViewModel() {

    //user init because there is no button of function performed on splash screen so we immediatelt need to call this function
    init {
        decideNavigation()
    }

    private val _splashstate= mutableStateOf<SplashState>(SplashState.Loading)
    val splashstate : State<SplashState> = _splashstate


    fun decideNavigation(){

        viewModelScope.launch{

            val user= authRepo.user()
            if(user==null){
                _splashstate.value= SplashState.GoToWelcome
            }
            else
             {
                val profile =userRepository.getUserProfile()
                if(profile==null){
                    _splashstate.value= SplashState.GoToUserDetails
                }
                else{
                    _splashstate.value= SplashState.GoToMain
                }
            }
        }
    }
}

sealed class SplashState {
    object Loading : SplashState()
    object GoToWelcome: SplashState()
    object GoToUserDetails: SplashState()
    object GoToMain : SplashState()
}