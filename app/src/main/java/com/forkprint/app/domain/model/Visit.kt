package com.forkprint.app.domain.model

import java.time.Instant

data class Visit(
    val id: String,
    val restaurant: Restaurant,
    val startedAt: Instant,
    val endedAt: Instant?,
    val rating: Int?,
    val note: String,
    val source: VisitSource,
) {
    init {
        require(rating == null || rating in 1..5) { "rating must be null or between 1 and 5" }
    }
}
