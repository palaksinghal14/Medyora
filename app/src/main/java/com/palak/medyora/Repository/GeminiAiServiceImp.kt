package com.palak.medyora.Repository

import android.util.Log
import com.palak.medyora.BuildConfig

class GeminiAiServiceImp(
     private val geminiClient : GeminiClient
): AiService {

    override suspend fun generateResponse(prompt: String): String {
       return try {
           geminiClient.generateContent(prompt)
       }catch (e: Exception){
            if (BuildConfig.DEBUG) Log.e("GeminiAI", "Error: ${e.message}", e)
           return "Sorry, something went wrong. Please try again."
       }
    }

}