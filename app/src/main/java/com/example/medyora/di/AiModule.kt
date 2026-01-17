package com.example.medyora.di

import com.example.medyora.Repository.AiService
import com.example.medyora.Repository.GeminiAiServiceImp
import com.example.medyora.Repository.GeminiClient
import com.example.medyora.Repository.SymptomAnalysisRepository
import com.example.medyora.Repository.SymptomAnalysisRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AiModule {
    @Provides
    @Singleton
    fun provideGeminiClient(): GeminiClient = GeminiClient()

    @Provides
    @Singleton
    fun provideAiService(
        geminiClient: GeminiClient
    ): AiService = GeminiAiServiceImp(geminiClient)

    @Provides
    @Singleton
    fun provideSymptomRepository(
        aiService: AiService
    ): SymptomAnalysisRepository =
        SymptomAnalysisRepositoryImp(aiService)
}