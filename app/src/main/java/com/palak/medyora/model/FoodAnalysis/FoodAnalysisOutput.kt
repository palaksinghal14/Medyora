package com.palak.medyora.model.FoodAnalysis

import com.palak.medyora.utils.AppException


sealed class FoodAnalysisOutput{

    data class FoodResult(
        val riskLevel: FoodRiskLevel,
        val summary:String,
        val impacts: List<String>,
        val portion :String,
        val alternatives :List<String>
    ):FoodAnalysisOutput()

    data class Error(
        val message: AppException
    ): FoodAnalysisOutput()
}
