package com.example.medyora.Repository

import com.example.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.example.medyora.model.FoodAnalysis.FoodAnalysisRequest

interface FoodAnalysisRepository {
    suspend fun analyzeFood( request : FoodAnalysisRequest): FoodAnalysisOutput
}