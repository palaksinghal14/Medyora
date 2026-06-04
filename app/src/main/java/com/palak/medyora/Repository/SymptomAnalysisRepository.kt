package com.palak.medyora.Repository

import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisRequest

interface SymptomAnalysisRepository {

    suspend fun analyzeInitialResponse(
        request: SymptomAnalysisRequest
    ): SymptomAnalysisOutput

    suspend fun analyzeFinalResponse(
        previousRequest: SymptomAnalysisRequest,
        followUpAnswer: String
    ): SymptomAnalysisOutput
}
