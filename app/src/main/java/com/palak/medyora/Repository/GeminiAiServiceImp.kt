package com.palak.medyora.Repository

import android.util.Log
import com.palak.medyora.BuildConfig

class GeminiAiServiceImp(
     private val geminiClient : GeminiClient
): AiService {

    override suspend fun generateResponse(prompt: String): String {

           if (BuildConfig.DEBUG) Log.e("GeminiAI", "Sending prompt to Gemini")
           return geminiClient.generateContent(prompt)

    }

}