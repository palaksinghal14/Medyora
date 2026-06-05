package com.palak.medyora.Repository

import android.util.Log
import com.palak.medyora.BuildConfig
import com.palak.medyora.Repository.PromptBuilding.buildFoodAnalysisPrompt
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisRequest
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.palak.medyora.utils.toAppException

class FoodAnalysisRepositoryImp(
    val aiService : AiService
): FoodAnalysisRepository {

    override suspend fun analyzeFood(request: FoodAnalysisRequest): FoodAnalysisOutput {

        return try {
            val prompt=buildFoodAnalysisPrompt(request)
            val aiResponse= aiService.generateResponse(prompt)
            if (BuildConfig.DEBUG) Log.d("AI_RAW", aiResponse)
            parseFoodAnalysisResponse(aiResponse)
        }catch (e: Exception){
            if (BuildConfig.DEBUG) Log.e("FoodRepo", "AI call failed: ${e.message}", e)
            FoodAnalysisOutput.Error(e.toAppException())
        }

    }
}