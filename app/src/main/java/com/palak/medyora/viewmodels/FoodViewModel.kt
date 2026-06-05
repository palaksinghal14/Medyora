package com.palak.medyora.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palak.medyora.BuildConfig
import com.palak.medyora.Repository.FoodAnalysisRepository
import com.palak.medyora.Repository.UserRepository
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisRequest
import com.palak.medyora.model.FoodAnalysis.FoodRiskLevel
import com.palak.medyora.model.SymptomAnalysis.UserHealthProfile
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class FoodFlowState {
    object Idle : FoodFlowState()
    object Loading : FoodFlowState()
    data class Result(
        val riskLevel: FoodRiskLevel,
        val summary:String,
        val impacts: List<String>,
        val portion :String,
        val alternatives :List<String>
    ): FoodFlowState()

    data class Error(val message: AppException) :FoodFlowState()
}


data class FoodInputUiState(
    val foodText: String = "",
    val error: AppException? = null
)


@HiltViewModel
class FoodViewModel @Inject constructor(
   val userRepository: UserRepository,
   val foodAnalysisRepository: FoodAnalysisRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(FoodInputUiState())
    val uiState = _uiState.asStateFlow()

    private val _flowState = MutableStateFlow<FoodFlowState>(FoodFlowState.Idle)
    val flowState = _flowState.asStateFlow()

    fun onFoodChange(text: String) {
        _uiState.update {
            it.copy(foodText = text)
        }
    }

    private suspend fun getUserHealthProfile(): UserHealthProfile? {
        val userProfile = userRepository.getUserProfile()

        return userProfile?.let{
            UserHealthProfile(
                age = it.age,
                gender = it.gender,
                activityLevel = it.activityLevel,
                knownConditions = it.conditions
            )
        }
    }

    private fun validateInput(food: String): Boolean {
        return when {
            food.isBlank() -> {
                _uiState.value = _uiState.value.copy(
                    error = AppException.UnknownException("Please enter a food item")
                )
                false
            }

            food.length < 2 -> {
                _uiState.value = _uiState.value.copy(
                    error = AppException.UnknownException("Food name too short")
                )
                false
            }

            else -> {
                _uiState.value = _uiState.value.copy(error = null)
                true
            }
        }
    }

    fun resetFoodFlow() {
        _uiState.value = FoodInputUiState()
        _flowState.value = FoodFlowState.Idle
    }

    fun analyzeFood()
    {
        val currentState = _uiState.value

        if(!validateInput(currentState.foodText))  return


         viewModelScope.launch {
             _uiState.update {
                 it.copy(
                     error = null
                 )
             }

             val userProfile=getUserHealthProfile()
             if(userProfile==null){
                 _uiState.update {
                     it.copy(
                         error = AppException.UnknownException("please fill in all user details ")
                     )
                 }
              return@launch
             }

             val foodRequest= FoodAnalysisRequest(
                 food=_uiState.value.foodText,
                 userProfile
             )

             _flowState.value= FoodFlowState.Loading

                 val foodResult= foodAnalysisRepository.analyzeFood(foodRequest)
                  if (BuildConfig.DEBUG) Log.d("FoodRepo", "Repo result = ${foodResult}")

                 when (foodResult) {
                     is FoodAnalysisOutput.FoodResult -> {
                         _flowState.value = FoodFlowState.Result(
                             riskLevel = foodResult.riskLevel,
                             summary = foodResult.summary,
                             impacts = foodResult.impacts,
                             portion = foodResult.portion,
                             alternatives = foodResult.alternatives
                         )

                     }

                     is FoodAnalysisOutput.Error -> {
                         _flowState.value = FoodFlowState.Error(foodResult.message)
                     }
                 }
    }
}
}