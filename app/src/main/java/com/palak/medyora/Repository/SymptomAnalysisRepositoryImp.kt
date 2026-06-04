package com.palak.medyora.Repository

import android.util.Log
import com.palak.medyora.Repository.PromptBuilding.buildInitialPrompt
import com.palak.medyora.Repository.PromptBuilding.buildFollowUpPrompt
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisRequest


class SymptomAnalysisRepositoryImp(
    private val aiService: AiService
) : SymptomAnalysisRepository{

    override suspend fun analyzeInitialResponse(request: SymptomAnalysisRequest): SymptomAnalysisOutput {

        val prompt= buildInitialPrompt(request)
        val aiResponse=aiService.generateResponse(prompt)
        Log.d("AI_RAW", aiResponse)
        return parseInitialResponse(aiResponse)

    }


    override suspend fun analyzeFinalResponse(
        previousRequest: SymptomAnalysisRequest,
        followUpAnswer: String
    ): SymptomAnalysisOutput {

        val prompt = buildFollowUpPrompt(previousRequest, followUpAnswer)
        val aiResponse = aiService.generateResponse(prompt)
        Log.d("AI_RAW", aiResponse)

        return parseFinalResponse(aiResponse)

    }
}
