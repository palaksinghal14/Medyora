package com.palak.medyora.model.SymptomAnalysis

import com.palak.medyora.utils.AppException

sealed class SymptomAnalysisOutput {
    data class RequiresFollowUp(
        val question: FollowUpQuestion
    ) : SymptomAnalysisOutput()

    data class FinalResult(
        val causes: List<String>,
        val riskLevel: RiskLevel,
        val recommendations: List<String>
    ) : SymptomAnalysisOutput()

    data class Error(val message: AppException) : SymptomAnalysisOutput()
}