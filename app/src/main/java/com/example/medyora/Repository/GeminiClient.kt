package com.example.medyora.Repository



import com.example.medyora.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel



class GeminiClient {

    private val model= GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    suspend fun generateContent(prompt: String): String {
        val response = model.generateContent(prompt)
        return response.text ?: throw IllegalStateException("Empty Gemini response")
    }
}