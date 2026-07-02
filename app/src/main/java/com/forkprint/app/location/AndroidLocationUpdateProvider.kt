package com.forkprint.app.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidLocationUpdateProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) : LocationUpdateProvider {
    private val client: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(context) }

    override fun foregroundLocationUpdates(): Flow<LocationUpdate> = callbackFlow {
        if (!context.hasLocationPermission()) {
            close(LocationPermissionMissingException())
            return@callbackFlow
        }
        val request = LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, UPDATE_INTERVAL_MS)
            .setMinUpdateIntervalMillis(MIN_UPDATE_INTERVAL_MS)
            .setMinUpdateDistanceMeters(MIN_DISTANCE_METERS)
            .setWaitForAccurateLocation(false)
            .build()
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.locations.forEach { location -> trySend(location.toUpdate()) }
            }
        }
        @Suppress("MissingPermission")
        client.requestLocationUpdates(request, callback, context.mainLooper)
            .addOnFailureListener { close(it) }
        awaitClose { client.removeLocationUpdates(callback) }
    }

    private fun Location.toUpdate() = LocationUpdate(
        latitude = latitude,
        longitude = longitude,
        accuracyMeters = if (hasAccuracy()) accuracy else null,
        observedAt = Instant.ofEpochMilli(time.takeIf { it > 0 } ?: System.currentTimeMillis()),
    )

    private fun Context.hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    private companion object {
        const val UPDATE_INTERVAL_MS = 15 * 60 * 1000L
        const val MIN_UPDATE_INTERVAL_MS = 5 * 60 * 1000L
        const val MIN_DISTANCE_METERS = 100f
    }
}

class LocationPermissionMissingException : SecurityException("Location permission has not been granted")
