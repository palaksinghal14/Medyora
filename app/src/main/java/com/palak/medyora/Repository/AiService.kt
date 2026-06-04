package com.palak.medyora.Repository


interface AiService {
    suspend fun generateResponse(prompt: String): String
}
