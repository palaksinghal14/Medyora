package com.palak.medyora.Repository

import com.palak.medyora.BuildConfig
import com.palak.medyora.model.NearbyDoctors.NearbyDoctor
import com.palak.medyora.model.NearbyDoctors.PlaceResult
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
import javax.inject.Inject

// data/repository/DoctorsRepository.kt
class DoctorsRepository @Inject constructor(
    private val placesApi: PlacesApiService,
    private val locationDataSource: LocationDataSource
) {

    suspend fun getNearbyDoctors(): Result<List<NearbyDoctor>> {
        return try {
            val location = locationDataSource.getLastKnownLocation()
                ?: return Result.failure(AppException.LocationPermissionDeniedException)

            val locationStr = "${location.latitude},${location.longitude}"

            val response = placesApi.getNearbyDoctors(
                location = locationStr,
                apiKey = BuildConfig.PLACES_API_KEY
            )

            if (response.status != "OK" && response.status != "ZERO_RESULTS") {
                return Result.failure(AppException.PlacesApiException)
            }

            val doctors = response.results.map { it.toDomain() }
            if(doctors.isEmpty()){
                return Result.failure(AppException.NoNearbyDoctorsException)
            }
            Result.success(doctors)

        } catch (e: Exception) {
            Result.failure(e.toAppException())
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

