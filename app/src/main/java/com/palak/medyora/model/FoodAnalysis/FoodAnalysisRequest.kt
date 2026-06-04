package com.palak.medyora.model.FoodAnalysis

import com.palak.medyora.model.SymptomAnalysis.UserHealthProfile

data class FoodAnalysisRequest(
    val food: String,
    val user: UserHealthProfile
)
