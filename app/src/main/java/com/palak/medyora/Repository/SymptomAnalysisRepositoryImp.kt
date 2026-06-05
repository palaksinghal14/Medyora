package com.palak.medyora.Repository

import android.util.Log
import com.palak.medyora.BuildConfig
import com.palak.medyora.Repository.PromptBuilding.buildInitialPrompt
import com.palak.medyora.Repository.PromptBuilding.buildFollowUpPrompt
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisRequest
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException


class SymptomAnalysisRepositoryImp(
    private val aiService: AiService
) : SymptomAnalysisRepository{

    override suspend fun analyzeInitialResponse(request: SymptomAnalysisRequest): SymptomAnalysisOutput {

        return try {

            val prompt= buildInitialPrompt(request)
            val aiResponse=aiService.generateResponse(prompt)
            if (BuildConfig.DEBUG) Log.d("AI_RAW", aiResponse)
            return parseInitialResponse(aiResponse)

        }catch (e: Exception){

            if (BuildConfig.DEBUG) Log.e("SymptomRepo", "AI call failed: ${e.message}", e)
            SymptomAnalysisOutput.Error(e.toAppException())

        }

    }


    override suspend fun analyzeFinalResponse(
        previousRequest: SymptomAnalysisRequest,
        followUpAnswer: String
    ): SymptomAnalysisOutput {

         return try {
             val prompt = buildFollowUpPrompt(previousRequest, followUpAnswer)
             val aiResponse = aiService.generateResponse(prompt)
             if (BuildConfig.DEBUG) Log.d("AI_RAW", aiResponse)
             parseFinalResponse(aiResponse)
         }catch (e: Exception){
             if (BuildConfig.DEBUG) Log.e("SymptomRepo", "Follow-up failed: ${e.message}", e)
             SymptomAnalysisOutput.Error(e.toAppException())
         }

    }
}
