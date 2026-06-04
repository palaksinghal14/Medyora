package com.palak.medyora.di

import com.palak.medyora.Repository.AiService
import com.palak.medyora.Repository.FoodAnalysisRepository
import com.palak.medyora.Repository.FoodAnalysisRepositoryImp
import com.palak.medyora.Repository.GeminiAiServiceImp
import com.palak.medyora.Repository.GeminiClient
import com.palak.medyora.Repository.SymptomAnalysisRepository
import com.palak.medyora.Repository.SymptomAnalysisRepositoryImp
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

    @Provides
    @Singleton
    fun  provideFoodRepository(
        aiService: AiService
    ): FoodAnalysisRepository= FoodAnalysisRepositoryImp(aiService)
}