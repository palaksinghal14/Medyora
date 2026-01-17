package com.example.medyora.model.SymptomAnalysis

data class UserHealthProfile(
    val age: Int,
    val gender: String,
    val activityLevel: String,
    val knownConditions: List<String>
)
