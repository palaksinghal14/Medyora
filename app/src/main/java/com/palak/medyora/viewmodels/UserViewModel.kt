package com.palak.medyora.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palak.medyora.Repository.UserRepository
import com.palak.medyora.model.UserProfile
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel()
{
    private val _state = mutableStateOf<UserProfileState>(UserProfileState.Idle )
    val state : State<UserProfileState> =_state

    fun SaveUserProfile( profile: UserProfile , onSuccess:() -> Unit) // iska mtlb h saveuser profile do cheejien lega ek toh user profile aur doosra ek lambda fucntion jiska mtlb h iske baad kuch hoga which is our navigation to main page
    {
        viewModelScope.launch {
            _state.value= UserProfileState.Loading
            userRepository.saveUserProfile(profile)
                .onSuccess {
                    userRepository.saveUserProfile(profile)
                    _state.value= UserProfileState.Success
                    onSuccess() // doing this because firestore calls are asynchronous and it said that after we will complete saving the data , we willperform some function
                }
                .onFailure { e->
                    _state.value= UserProfileState.Error(e.toAppException())

                }
        }
    }

    private val _userState= mutableStateOf<UserProfile?>(null)
    val userState : State<UserProfile?> = _userState
    fun getUserProfile( OnResult: (UserProfile?)-> Unit) {

        viewModelScope.launch {
            _state.value= UserProfileState.Loading
            userRepository.getUserProfile()
                .onSuccess { profile ->
                    if(profile==null){
                        _state.value= UserProfileState.Error(AppException.UserNotFoundException)
                    }else{
                        _userState.value = profile
                        _state.value= UserProfileState.Success
                        OnResult(profile)
                    }
                }
                .onFailure { e->
                    _state.value=UserProfileState.Error( e.toAppException())
                    OnResult(null)
                }
        }

    }
}

sealed class UserProfileState{
    object Idle : UserProfileState()
    object Loading : UserProfileState()
     object Success: UserProfileState()
    data class Error(val msg: AppException) : UserProfileState()
}
