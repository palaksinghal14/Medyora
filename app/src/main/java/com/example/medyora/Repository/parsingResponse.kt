package com.example.medyora.Repository

import com.example.medyora.model.SymptomAnalysis.FollowUpQuestion
import com.example.medyora.model.SymptomAnalysis.RiskLevel
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisOutput
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisResult
import org.json.JSONObject


fun parseInitialResponse(jsonString: String) : SymptomAnalysisOutput{
  return try {
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
        message = "Failed to parse initial AI response"
    )
  }
}

fun parseFinalResponse(jsonString: String): SymptomAnalysisOutput{
    return try {
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
