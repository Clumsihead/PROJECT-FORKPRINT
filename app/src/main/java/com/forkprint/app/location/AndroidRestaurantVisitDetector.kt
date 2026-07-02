package com.forkprint.app.location

import com.forkprint.app.domain.model.VisitDraft
import com.forkprint.app.places.PlaceCandidate
import com.forkprint.app.places.PlacesProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Singleton
class AndroidRestaurantVisitDetector @Inject constructor(
    private val locationUpdateProvider: LocationUpdateProvider,
    private val placesProvider: PlacesProvider,
) : RestaurantVisitDetector {
    private val session = VisitDetectionSession()

    override fun detectedVisits(): Flow<VisitDraft> {
        return locationUpdateProvider.foregroundLocationUpdates()
            .mapNotNull { update ->
                val nearby = placesProvider.nearbyRestaurants(update.latitude, update.longitude)
                nearby.asSequence()
                    .filter { it.latitude != null && it.longitude != null && it.googlePlaceId != null }
                    .map { place -> place to distanceMeters(update.latitude, update.longitude, place) }
                    .filter { (_, distance) -> distance <= MAX_DISTANCE_METERS }
                    .minByOrNull { (_, distance) -> distance }
                    ?.let { (place, distance) ->
                        session.record(PlaceObservation(place, distance, update.observedAt))
                    }
            }
            .catch { emitAllEmpty() }
    }

    private suspend fun kotlinx.coroutines.flow.FlowCollector<VisitDraft>.emitAllEmpty() = Unit

    private fun distanceMeters(latitude: Double, longitude: Double, place: PlaceCandidate): Double {
        val placeLatitude = place.latitude ?: return Double.MAX_VALUE
        val placeLongitude = place.longitude ?: return Double.MAX_VALUE
        val earthRadiusMeters = 6_371_000.0
        val dLat = Math.toRadians(placeLatitude - latitude)
        val dLon = Math.toRadians(placeLongitude - longitude)
        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(latitude)) * cos(Math.toRadians(placeLatitude)) *
            sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadiusMeters * c
    }

    private companion object { const val MAX_DISTANCE_METERS = 100.0 }
}
