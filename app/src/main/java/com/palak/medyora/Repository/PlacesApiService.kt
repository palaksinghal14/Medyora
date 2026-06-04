package com.palak.medyora.Repository

import com.palak.medyora.model.NearbyDoctors.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

// data/remote/PlacesApiService.kt
interface PlacesApiService {

    @GET("maps/api/place/nearbysearch/json")
    suspend fun getNearbyDoctors(
        @Query("location")  location: String,   // "lat,lng"
        @Query("radius")    radius: Int = 5000, // meters
        @Query("type")      type: String = "doctor",
        @Query("key")       apiKey: String
    ): PlacesResponse
}