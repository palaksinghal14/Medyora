package com.example.medyora.Repository

import com.example.medyora.Repository.PromptBuilding.buildInitialPrompt
import com.example.medyora.Repository.PromptBuilding.buildFollowUpPrompt
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisRequest


class SymptomAnalysisRepositoryImp(
    private val aiService: AiService
) : SymptomAnalysisRepository{

    override suspend fun analyzeInitialResponse(request: SymptomAnalysisRequest): SymptomAnalysisOutput {
        val prompt= buildInitialPrompt(request)
        val aiResponse=aiService.generateResponse(prompt)

        return parseInitialResponse(aiResponse)
    }

    override suspend fun analyzeFinalResponse(
        previousRequest: SymptomAnalysisRequest,
        followUpAnswer: String
    ): SymptomAnalysisOutput {

        val prompt = buildFollowUpPrompt(previousRequest, followUpAnswer)
        val aiResponse = aiService.generateResponse(prompt)

        return parseFinalResponse(aiResponse)
    }
}
