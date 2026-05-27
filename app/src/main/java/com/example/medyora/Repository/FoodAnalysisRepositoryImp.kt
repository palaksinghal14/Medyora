package com.example.medyora.Repository

import android.util.Log
import com.example.medyora.Repository.PromptBuilding.buildFoodAnalysisPrompt
import com.example.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.example.medyora.model.FoodAnalysis.FoodAnalysisRequest

class FoodAnalysisRepositoryImp(
    val aiService : AiService
): FoodAnalysisRepository {

    override suspend fun analyzeFood(request: FoodAnalysisRequest): FoodAnalysisOutput {
        val prompt=buildFoodAnalysisPrompt(request)
        val aiResponse= aiService.generateResponse(prompt)
        Log.d("AI_RAW", aiResponse)
        return parseFoodAnalysisResponse(aiResponse)
    }
}