package com.palak.medyora.Repository

import android.util.Log
import com.palak.medyora.BuildConfig
import com.palak.medyora.Repository.PromptBuilding.buildFoodAnalysisPrompt
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisRequest

class FoodAnalysisRepositoryImp(
    val aiService : AiService
): FoodAnalysisRepository {

    override suspend fun analyzeFood(request: FoodAnalysisRequest): FoodAnalysisOutput {
        val prompt=buildFoodAnalysisPrompt(request)
        val aiResponse= aiService.generateResponse(prompt)
         if (BuildConfig.DEBUG) Log.d("AI_RAW", aiResponse)
        return parseFoodAnalysisResponse(aiResponse)
    }
}