package com.example.medyora.Repository

import com.example.medyora.model.SymptomAnalysis.FollowUpQuestion
import com.example.medyora.model.SymptomAnalysis.RiskLevel
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisResult
import org.json.JSONObject


fun parseInitialResponse(raw : String) : SymptomAnalysisOutput{
  return try {

      // 1. Extract JSON block from AI text
      val jsonString = extractJson(raw)
          ?: return SymptomAnalysisOutput.Error("AI did not return valid JSON")

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
        "Failed to parse initial AI response: ${e.message}"
    )
  }
}

fun parseFinalResponse(raw: String): SymptomAnalysisOutput{
    return try {

        val jsonString = extractJson(raw)
            ?: return SymptomAnalysisOutput.Error("AI did not return valid JSON")


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
            message = "Failed to parse final AI response"
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



