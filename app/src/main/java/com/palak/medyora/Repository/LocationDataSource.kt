package com.palak.medyora.Repository

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// data/location/LocationDataSource.kt
class LocationDataSource @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient
) {

    // Returns last known location as a one-shot suspend call
    // Uses kotlinx-coroutines-play-services for .await()
    @SuppressLint("MissingPermission")
    suspend fun getLastKnownLocation(): android.location.Location? {
        return fusedLocationClient.lastLocation.await()
    }
}