package com.example.medyora.model.SymptomAnalysis

data class SymptomAnalysisResult(
    val requiresFollowUp: Boolean,
    val followUpQuestion: FollowUpQuestion? = null,

    val causes: List<String>?= null,
    val riskLevel: RiskLevel ,
    val recommendations: List<String>? = null
)
