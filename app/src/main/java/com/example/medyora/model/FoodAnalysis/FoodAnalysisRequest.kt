package com.example.medyora.model.FoodAnalysis

import com.example.medyora.model.SymptomAnalysis.UserHealthProfile

data class FoodAnalysisRequest(
    val food: String,
    val user: UserHealthProfile
)
