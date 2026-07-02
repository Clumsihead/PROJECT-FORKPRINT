package com.forkprint.app.location

import com.forkprint.app.domain.model.VisitDraft
import com.forkprint.app.places.PlaceCandidate
import java.time.Duration
import java.time.Instant

data class PlaceObservation(
    val place: PlaceCandidate,
    val distanceMeters: Double,
    val observedAt: Instant,
)

class VisitDetectionSession(
    private val minDwell: Duration = Duration.ofMinutes(20),
) {
    private val firstSeenByPlaceId = mutableMapOf<String, PlaceObservation>()
    private val emittedPlaceIds = mutableSetOf<String>()

    fun record(observation: PlaceObservation): VisitDraft? {
        val placeId = observation.place.googlePlaceId ?: return null
        if (placeId in emittedPlaceIds) return null
        val firstSeen = firstSeenByPlaceId.getOrPut(placeId) { observation }
        val dwellMinutes = Duration.between(firstSeen.observedAt, observation.observedAt).toMinutes().coerceAtLeast(0)
        val signal = RestaurantCandidateSignal(
            distanceMeters = observation.distanceMeters,
            dwellMinutes = dwellMinutes,
            confidence = confidenceFor(observation.place, observation.distanceMeters),
        )
        return if (VisitDetectionEngine.isLikelyVisit(signal)) {
            emittedPlaceIds += placeId
            VisitDraftFactory.fromDetectedCandidate(observation.place, firstSeen.observedAt)
        } else {
            null
        }
    }

    private fun confidenceFor(place: PlaceCandidate, distanceMeters: Double): Double {
        val categoryBoost = if (place.categories.any { it in RESTAURANT_TYPES }) 0.35 else 0.0
        val distanceScore = when {
            distanceMeters <= 25 -> 0.45
            distanceMeters <= 50 -> 0.35
            distanceMeters <= 75 -> 0.25
            else -> 0.0
        }
        return (categoryBoost + distanceScore).coerceAtMost(1.0)
    }

    private companion object {
        val RESTAURANT_TYPES = setOf("restaurant", "cafe", "bakery", "bar", "meal_takeaway", "meal_delivery")
    }
}
