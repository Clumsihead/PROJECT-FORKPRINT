package com.forkprint.app.location

data class RestaurantCandidateSignal(
    val distanceMeters: Double,
    val dwellMinutes: Long,
    val confidence: Double,
)

object VisitDetectionEngine {
    private const val MAX_RESTAURANT_DISTANCE_METERS = 75.0
    private const val MIN_DWELL_MINUTES = 20L
    private const val MIN_CONFIDENCE = 0.65

    fun isLikelyVisit(candidate: RestaurantCandidateSignal): Boolean {
        return candidate.distanceMeters <= MAX_RESTAURANT_DISTANCE_METERS &&
            candidate.dwellMinutes >= MIN_DWELL_MINUTES &&
            candidate.confidence >= MIN_CONFIDENCE
    }
}
