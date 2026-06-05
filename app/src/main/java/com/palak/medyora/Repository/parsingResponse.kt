package com.palak.medyora.Repository


import com.palak.medyora.model.FoodAnalysis.FoodAnalysisOutput
import com.palak.medyora.model.FoodAnalysis.FoodRiskLevel
import com.palak.medyora.model.SymptomAnalysis.FollowUpQuestion
import com.palak.medyora.model.SymptomAnalysis.RiskLevel
import com.palak.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.palak.medyora.utils.AppException
import org.json.JSONObject


fun parseInitialResponse(raw : String) : SymptomAnalysisOutput{
  return try {

      // 1. Extract JSON block from AI text
      val jsonString = extractJson(raw)
          ?: return SymptomAnalysisOutput.Error(AppException.AiResponseParseException)

      val json = JSONObject(jsonString)

      val requiresFollowUp = json.getBoolean("requiresFollowUp")
      if (requiresFollowUp) {
          val fq = json.getJSONObject("followUpQuestion")
          val question = fq.getString("question")

          val optionsArray = fq.getJSONArray("options")
          val options = List(optionsArray.length()) { i ->
              optionsArray.getString(i)
          }
          SymptomAnalysisOutput.RequiresFollowUp(
              question = FollowUpQuestion(
                  question = question,
                  options = options
              )
          )
      } else
      {
        val causesArray = json.getJSONArray("causes")
        val causes = List(causesArray.length()) { i ->
            causesArray.getString(i)
        }

        val riskLevelString = json.getString("riskLevel")
          val riskLevel = RiskLevel.valueOf(
              riskLevelString.uppercase()
          )

        val recommendationsArray = json.getJSONArray("recommendations")
        val recommendations = List(recommendationsArray.length()) { i ->
            recommendationsArray.getString(i)
        }

        SymptomAnalysisOutput.FinalResult(
            causes = causes,
            riskLevel = riskLevel,
            recommendations = recommendations
        )
      }
  }catch (e: Exception){
    SymptomAnalysisOutput.Error(
        AppException.AiResponseParseException
    )
  }
}

fun parseFinalResponse(raw: String): SymptomAnalysisOutput{
    return try {

        val jsonString = extractJson(raw)
            ?: return SymptomAnalysisOutput.Error(AppException.AiResponseParseException)


        val json = JSONObject(jsonString)

        val causesArray = json.getJSONArray("causes")
        val causes = List(causesArray.length()) { i ->
            causesArray.getString(i)
        }

        val riskLevelString = json.getString("riskLevel")
        val riskLevel = RiskLevel.valueOf(
            riskLevelString.uppercase()
        )

        val recommendationsArray = json.getJSONArray("recommendations")
        val recommendations = List(recommendationsArray.length()) { i ->
            recommendationsArray.getString(i)
        }

        SymptomAnalysisOutput.FinalResult(
            causes = causes,
            riskLevel = riskLevel,
            recommendations = recommendations
        )
    } catch (e: Exception) {
        SymptomAnalysisOutput.Error(
            AppException.AiResponseParseException
        )
    }
}


fun parseFoodAnalysisResponse(raw: String): FoodAnalysisOutput {
    return try {

        val jsonString = extractJson(raw)
            ?: return FoodAnalysisOutput.Error("Invalid AI response (no JSON found)")

        val json = JSONObject(jsonString)

        val foodRisk = FoodRiskLevel.valueOf(
            json.getString("foodRisk").trim().uppercase()
        )

        val summary = json.getString("summary")

        val impactsArray = json.getJSONArray("impacts")
        val impacts = List(impactsArray.length()) { i ->
            impactsArray.getString(i)
        }

        val portion = json.getString("portionGuidance")

        val alternativesArray = json.getJSONArray("alternatives")
        val alternatives = List(alternativesArray.length()) { i ->
            alternativesArray.getString(i)
        }

        return FoodAnalysisOutput.FoodResult(
            riskLevel = foodRisk,
            summary = summary,
            impacts = impacts,
            portion = portion,
            alternatives = alternatives
        )

    } catch (e: Exception) {
        FoodAnalysisOutput.Error(
            "Failed to parse food analysis: ${e.message}"
        )
    }
}

fun extractJson(text: String): String? {
    // Remove code fences if present
    val cleaned = text
        .replace("```json", "")
        .replace("```", "")
        .trim()

    // Find first { and last }
    val start = cleaned.indexOf('{')
    val end = cleaned.lastIndexOf('}')

    if (start == -1 || end == -1 || end <= start) {
      throw IllegalArgumentException("No valid json found")
    }

    return cleaned.substring(start, end + 1)
}



