package com.forkprint.app.domain.model

import java.time.Instant

data class JourneyMap(
    val places: List<JourneyPlace>,
    val clusters: List<JourneyCluster>,
    val totalMappedVisits: Int,
    val unmappedVisitCount: Int,
)

data class JourneyPlace(
    val restaurant: Restaurant,
    val latitude: Double,
    val longitude: Double,
    val visitCount: Int,
    val firstVisitAt: Instant,
    val mostRecentVisitAt: Instant,
    val averageRating: Double?,
)

data class JourneyCluster(
    val latitude: Double,
    val longitude: Double,
    val places: List<JourneyPlace>,
) {
    val visitCount: Int = places.sumOf { it.visitCount }
}
