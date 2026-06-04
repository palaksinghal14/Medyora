package com.palak.medyora.Repository

import com.palak.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.palak.medyora.model.FoodAnalysis.FoodAnalysisRequest

interface FoodAnalysisRepository {
    suspend fun analyzeFood( request : FoodAnalysisRequest): FoodAnalysisOutput
}