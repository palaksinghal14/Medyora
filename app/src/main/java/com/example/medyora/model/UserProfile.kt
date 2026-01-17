package com.example.medyora.model

data class UserProfile(
    val uid : String="",

    // Personal Details
    val name: String = "",
    val age: Int = 0,
    val dob: String = "",
    val gender: String = "",
    val contact: String = "",

    // Physical Details
    val heightCm: Int = 0,
    val weightKg: Int = 0,
    val activityLevel: String = "",

    // Medical Details
    val conditions: List<String> = emptyList(),

    // Timestamps
    val createdAt: Long = System.currentTimeMillis()
)
