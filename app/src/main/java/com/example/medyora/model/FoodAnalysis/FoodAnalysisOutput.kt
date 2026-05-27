package com.example.medyora.model.FoodAnalysis


sealed class FoodAnalysisOutput{

    data class FoodResult(
        val riskLevel: FoodRiskLevel,
        val summary:String,
        val impacts: List<String>,
        val portion :String,
        val alternatives :List<String>
    ):FoodAnalysisOutput()

    data class Error(
        val message: String
    ): FoodAnalysisOutput()
}
