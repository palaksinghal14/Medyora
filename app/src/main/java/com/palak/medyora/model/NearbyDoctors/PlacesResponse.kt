package com.palak.medyora.model.NearbyDoctors

import com.google.gson.annotations.SerializedName

// data/remote/dto/PlacesResponse.kt
data class PlacesResponse(
    val results: List<PlaceResult>,
    val status: String
)

data class PlaceResult(
    @SerializedName("place_id")   val placeId: String,
    @SerializedName("name")       val name: String,
    @SerializedName("vicinity")   val vicinity: String,
    @SerializedName("rating")     val rating: Float?,
    @SerializedName("geometry")   val geometry: Geometry,
    @SerializedName("opening_hours") val openingHours: OpeningHours?
)

data class Geometry(val location: LatLngDto)

data class LatLngDto(val lat: Double, val lng: Double)

data class OpeningHours(
    @SerializedName("open_now") val openNow: Boolean?
)