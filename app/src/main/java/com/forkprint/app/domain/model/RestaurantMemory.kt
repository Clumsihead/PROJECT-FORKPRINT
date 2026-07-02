package com.forkprint.app.domain.model

import java.time.Instant

data class RestaurantMemory(
    val restaurant: Restaurant,
    val visitCount: Int,
    val firstVisitAt: Instant?,
    val mostRecentVisitAt: Instant?,
    val averageRating: Double?,
    val notes: List<String>,
    val visits: List<Visit>,
)
