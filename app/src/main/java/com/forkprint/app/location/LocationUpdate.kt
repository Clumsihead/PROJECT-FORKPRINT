package com.forkprint.app.location

import java.time.Instant

data class LocationUpdate(
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float?,
    val observedAt: Instant,
)

interface LocationUpdateProvider {
    fun foregroundLocationUpdates(): kotlinx.coroutines.flow.Flow<LocationUpdate>
}
