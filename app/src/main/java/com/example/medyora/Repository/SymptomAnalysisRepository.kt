package com.example.medyora.Repository

import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisRequest

interface SymptomAnalysisRepository {

    suspend fun analyzeInitialResponse(
        request: SymptomAnalysisRequest
    ): SymptomAnalysisOutput

    suspend fun analyzeFinalResponse(
        previousRequest: SymptomAnalysisRequest,
        followUpAnswer: String
    ): SymptomAnalysisOutput
}
