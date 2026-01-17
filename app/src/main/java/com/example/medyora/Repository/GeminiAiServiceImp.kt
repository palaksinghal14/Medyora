package com.example.medyora.Repository

class GeminiAiServiceImp(
     private val geminiClient : GeminiClient
): AiService {

    override suspend fun generateResponse(prompt: String): String {
       return try {
           geminiClient.generateContent(prompt)
       }catch (e: Exception){
           throw RuntimeException("AI processing failed",e)
       }
    }

}