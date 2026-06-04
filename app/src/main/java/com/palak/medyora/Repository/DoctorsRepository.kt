package com.palak.medyora.Repository

import com.palak.medyora.BuildConfig
import com.palak.medyora.model.NearbyDoctors.NearbyDoctor
import com.palak.medyora.model.NearbyDoctors.PlaceResult
import javax.inject.Inject

// data/repository/DoctorsRepository.kt
class DoctorsRepository @Inject constructor(
    private val placesApi: PlacesApiService,
    private val locationDataSource: LocationDataSource
) {

    suspend fun getNearbyDoctors(): Result<List<NearbyDoctor>> {
        return try {
            val location = locationDataSource.getLastKnownLocation()
                ?: return Result.failure(Exception("Unable to get location"))

            val locationStr = "${location.latitude},${location.longitude}"

            val response = placesApi.getNearbyDoctors(
                location = locationStr,
                apiKey = BuildConfig.PLACES_API_KEY
            )

            if (response.status != "OK" && response.status != "ZERO_RESULTS") {
                return Result.failure(Exception("Places API error: ${response.status}"))
            }

            val doctors = response.results.map { it.toDomain() }
            Result.success(doctors)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Mapper extension
fun PlaceResult.toDomain() = NearbyDoctor(
    placeId  = placeId,
    name     = name,
    address  = vicinity,
    rating   = rating,
    isOpen   = openingHours?.openNow,
    latitude = geometry.location.lat,
    longitude = geometry.location.lng
)

