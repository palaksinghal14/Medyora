package com.example.medyora.Repository


interface AiService {
    suspend fun generateResponse(prompt: String): String
}
