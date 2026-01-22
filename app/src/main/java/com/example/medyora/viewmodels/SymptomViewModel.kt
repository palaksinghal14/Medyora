package com.example.medyora.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medyora.Repository.SymptomAnalysisRepository
import com.example.medyora.Repository.UserRepository
import com.example.medyora.model.SymptomAnalysis.RiskLevel
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisRequest
import com.example.medyora.model.SymptomAnalysis.SymptomDuration
import com.example.medyora.model.SymptomAnalysis.SymptomInput
import com.example.medyora.model.SymptomAnalysis.SymptomSeverity
import com.example.medyora.model.SymptomAnalysis.UserHealthProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SymptomInputUiState(
    val symptomText: String = "",
    val duration: SymptomDuration? = null,
    val severity: SymptomSeverity? = null,
    val errorMessage: String? = null
)


@HiltViewModel
class SymptomViewModel @Inject constructor(
    val userRepository: UserRepository,
    val symptomRepo: SymptomAnalysisRepository
): ViewModel()
{
    //handles textfields
    private val _uiState = MutableStateFlow(SymptomInputUiState())
    val uiState: StateFlow<SymptomInputUiState> = _uiState.asStateFlow()

    //decides which screen to show
    private val _flowState = MutableStateFlow<SymptomFlowState>(SymptomFlowState.Idle)
    val flowState: StateFlow<SymptomFlowState> = _flowState.asStateFlow()

    private var cachedRequest: SymptomAnalysisRequest?=null

    //update functions for UI state update
    fun onSymptomTextChange(text: String) {
        _uiState.update {
            it.copy(symptomText = text)
        }
    }

    fun onDurationSelected(duration: SymptomDuration) {
        _uiState.update {
            it.copy(duration = duration)
        }
    }

    fun onSeveritySelected(severity: SymptomSeverity) {
        _uiState.update {
            it.copy(severity = severity)
        }
    }

    // functiom to fetch user profile and keeping only the necessary details
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

    private fun validateInput(state: SymptomInputUiState): Boolean {
        return state.symptomText.isNotBlank()
                && state.duration != null
                && state.severity != null
    }

    fun analyzeSymptom(){

        val currentState = _uiState.value // this will give us the updated value of all the input textfields that we have taken as Ui state

        if (!validateInput(currentState))
        {
            _uiState.update {
                it.copy(errorMessage = "Please fill all fields")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy( errorMessage = null) }

            val healthProfile = getUserHealthProfile()

            if (healthProfile == null) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Please complete your health profile before symptom analysis"
                    )
                }
                return@launch
            }

            val request = SymptomAnalysisRequest(
                symptomInput = SymptomInput(
                    description = currentState.symptomText,
                    duration = currentState.duration!!,
                    severity = currentState.severity!!
                ),
                userHealthProfile = healthProfile
            )

            cachedRequest = request

            //  AI call

            _flowState.value= SymptomFlowState.Loading
            val result = symptomRepo.analyzeInitialResponse(request)
            Log.d("SymptomRepo", "Repo result = $result")
            when (result) {
                is SymptomAnalysisOutput.RequiresFollowUp -> {
                    _flowState.value = SymptomFlowState.
                    FollowUp(
                        question = result.question.question,
                        options = result.question.options
                        )
                }

                is SymptomAnalysisOutput.FinalResult -> {
                    _flowState.value = SymptomFlowState.Result(
                        causes = result.causes,
                        riskLevel = result.riskLevel,
                        recommendation = result.recommendations
                    )
                }

                is SymptomAnalysisOutput.Error -> {
                    _flowState.value = SymptomFlowState.Error(result.message)
                }
            }

        }

    }


    //followup answer logic handle
    fun submitFollowUpAnswer(answer: String) {
        viewModelScope.launch {

            _flowState.value= SymptomFlowState.Loading

            val request = cachedRequest?:run{
                _flowState.value= SymptomFlowState.Error("session expired")
                return@launch
            }

            when (val result = symptomRepo.analyzeFinalResponse(request, answer)) {
                is SymptomAnalysisOutput.FinalResult -> {
                    _flowState.value = SymptomFlowState.Result(
                        causes = result.causes,
                        riskLevel = result.riskLevel,
                        recommendation = result.recommendations
                    )
                }

                is SymptomAnalysisOutput.Error -> {
                    _flowState.value = SymptomFlowState.Error(result.message)
                }

                else -> {
                    _flowState.value = SymptomFlowState.Error("Unexpected response")
                }
            }

        }
    }


    fun resetSymptomFlow() {
        _uiState.value = SymptomInputUiState()
        _flowState.value = SymptomFlowState.Idle
    }

}

sealed class SymptomFlowState {
    object Idle : SymptomFlowState()
    object Loading : SymptomFlowState()

    data class FollowUp(
        val question: String,
        val options: List<String>? = emptyList()
    ) : SymptomFlowState()

    data class Result(
        val causes: List<String>,
        val riskLevel: RiskLevel,
        val recommendation: List<String>
    ) : SymptomFlowState()

    data class Error(val message: String) : SymptomFlowState()
}