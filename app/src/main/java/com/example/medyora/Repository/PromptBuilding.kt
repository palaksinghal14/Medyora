package com.example.medyora.Repository

import com.example.medyora.model.SymptomAnalysis.SymptomAnalysisRequest

object PromptBuilding {


   fun buildInitialPrompt(
        request: SymptomAnalysisRequest
    ): String {
        return """
You are a medical assistant AI.

Analyze the following patient data and decide:
1. Whether follow-up questions are required.
2. If requiresFollowUp is true, ask ONE clear follow-up question.
3.If requiresFollowUp is false, set followUpQuestion to null.
3. If not required, provide final analysis.


Patient Data:
- Symptoms: ${request.symptomInput.description}
- Duration: ${request.symptomInput.duration}
- Severity: ${request.symptomInput.severity}
- Age: ${request.userHealthProfile?.age?: "Unknown"}
- Gender: ${request.userHealthProfile?.gender?: "Unknown"}
- Activity Level: ${request.userHealthProfile?.activityLevel?: "Unknown"}
- Known Conditions: ${request.userHealthProfile?.knownConditions?.joinToString()?: "Unknown"}

Respond strictly in this JSON format:

{
  "requiresFollowUp": true/false,
  "followUpQuestion": {
     "question": "...",
     "options": ["Yes", "No"]
  },
  "causes": ["...", "..."],
  "riskLevel": "LOW | MODERATE | HIGH",
  "recommendations": ["...", "..."]
}


Rules:
- Ask follow-up ONLY if necessary
- Do not add explanations outside JSON
-riskLevel must be exactly one of: LOW, MODERATE, HIGH

"""
    }

 fun buildFollowUpPrompt(
        request: SymptomAnalysisRequest,
        followUpAnswer: String
    ): String {
        return """
You are a medical assistant AI.

The user has answered the follow-up question.

Follow-up answer: "$followUpAnswer"

Based on the full context below, provide FINAL analysis.

Context:
- Symptoms: ${request.symptomInput.description}
- Duration: ${request.symptomInput.duration}
- Severity: ${request.symptomInput.severity}
- Age: ${request.userHealthProfile?.age?: "Unknown"}
- Gender: ${request.userHealthProfile?.gender?: "Unknown"}
- Activity Level: ${request.userHealthProfile?.activityLevel?: "Unknown"}
- Known Conditions: ${request.userHealthProfile?.knownConditions?.joinToString()?: "Unknown"}

Respond strictly in this JSON format:

{
  "causes": ["...", "..."],
  "riskLevel": "LOW | MODERATE | HIGH",
  "recommendations": ["...", "..."]
}
 
 rules:
- Do not add explanations outside JSON
-riskLevel must be exactly one of: LOW, MODERATE, HIGH
"""
    }


}