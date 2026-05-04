package com.example.medyora.model.FoodAnalysis

data class FoodAnalysisOutput(
    val riskLevel: FoodRiskLevel,
    val summary:String,
    val impacts: List<String>,
    val portion :List<String>,
    val alternatives :String
)
