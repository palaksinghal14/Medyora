package com.example.medyora.model.SymptomAnalysis

data class SymptomAnalysisRequest (
    val symptomInput: SymptomInput,
    val userHealthProfile:  UserHealthProfile?
)