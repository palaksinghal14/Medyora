package com.palak.medyora.model.NearbyDoctors

// data/model/NearbyDoctor.kt

data class NearbyDoctor(
    val placeId: String,
    val name: String,
    val address: String,
    val rating: Float?,
    val isOpen: Boolean?,
    val latitude: Double,
    val longitude: Double
)