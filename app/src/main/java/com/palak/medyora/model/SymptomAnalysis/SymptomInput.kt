package com.palak.medyora.model.SymptomAnalysis

data class SymptomInput(
    val description: String,
    val duration: SymptomDuration,
    val severity: SymptomSeverity
)
