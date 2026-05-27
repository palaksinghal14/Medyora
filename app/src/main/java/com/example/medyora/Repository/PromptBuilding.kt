package com.example.medyora.Repository

import com.example.medyora.model.FoodAnalysis.FoodAnalysisRequest
import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisRequest

object PromptBuilding {


   fun buildInitialPrompt(
        request: SymptomAnalysisRequest
    ): String {
       return """
You are a medical triage AI.

Your task is to analyze the patient data and return ONLY a valid JSON object.
Do NOT add any explanation, text, markdown, or commentary.
Do NOT use code fences.
Output ONLY raw JSON.

You must follow ONE of the two formats below.

FORMAT 1: If follow-up is required

{
  "requiresFollowUp": true,
  "followUpQuestion": {
    "question": "string",
    "options": ["string", "string"]
  }
}

FORMAT 2: If NO follow-up is required

{
  "requiresFollowUp": false,
  "causes": ["string", "string"],
  "riskLevel": "LOW | MODERATE | HIGH",
  "recommendations": ["string", "string"]
}

Patient Data:
- Symptoms: ${request.symptomInput.description}
- Duration: ${request.symptomInput.duration}
- Severity: ${request.symptomInput.severity}
- Age: ${request.userHealthProfile?.age ?: "Unknown"}
- Gender: ${request.userHealthProfile?.gender ?: "Unknown"}
- Activity Level: ${request.userHealthProfile?.activityLevel ?: "Unknown"}
- Known Conditions: ${request.userHealthProfile?.knownConditions?.joinToString() ?: "Unknown"}

Rules:
- Output ONLY one JSON object
- No text before or after JSON
- No markdown
- riskLevel must be exactly: LOW, MODERATE, or HIGH
""".trimIndent()
    }

 fun buildFollowUpPrompt(
        request: SymptomAnalysisRequest,
        followUpAnswer: String
    ): String {
     return """
You are a medical triage AI.

Based on the full context, return ONLY a valid JSON object.
Do NOT add any explanation, text, markdown, or commentary.
Do NOT use code fences.
Output ONLY raw JSON.

Output format:

{
  "causes": ["string", "string"],
  "riskLevel": "LOW | MODERATE | HIGH",
  "recommendations": ["string", "string"]
}

Context:
- Symptoms: ${request.symptomInput.description}
- Duration: ${request.symptomInput.duration}
- Severity: ${request.symptomInput.severity}
- Age: ${request.userHealthProfile?.age ?: "Unknown"}
- Gender: ${request.userHealthProfile?.gender ?: "Unknown"}
- Activity Level: ${request.userHealthProfile?.activityLevel ?: "Unknown"}
- Known Conditions: ${request.userHealthProfile?.knownConditions?.joinToString() ?: "Unknown"}

Follow-up Answer:
"$followUpAnswer"

Rules:
- Output ONLY raw JSON
- No text outside JSON
- riskLevel must be exactly: LOW, MODERATE, or HIGH
""".trimIndent()

 }

    fun buildFoodAnalysisPrompt(
        request: FoodAnalysisRequest
    ): String {
        return """
You are a medical nutrition AI.

Your task is to analyze food safety for a user based on their health profile.

You must return ONLY valid JSON.
No explanation, no markdown, no text outside JSON.

OUTPUT FORMAT:

{
  "foodRisk": "SAFE | CAUTION | RISKY | UNSAFE",
  "summary": "string (2-3 lines max)",
  "impacts": ["string", "string"],
  "portionGuidance": "string",
  "alternatives": ["string", "string"]
}

INPUT DATA:
- Food: ${request.food}
- Age: ${request.user.age}
- Known Conditions: ${request.user.knownConditions.joinToString()}

RULES:
- foodRisk must be EXACTLY one of: SAFE, CAUTION, RISKY, UNSAFE
- impacts = health effects of consuming this food
- alternatives = safer substitute foods
- portionGuidance = clear consumption advice
- summary = short medical-style explanation
- STRICT JSON ONLY
""".trimIndent()
    }
}