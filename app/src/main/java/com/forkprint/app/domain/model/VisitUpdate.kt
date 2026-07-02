package com.forkprint.app.domain.model

import java.time.Instant

data class VisitUpdate(
    val restaurantName: String,
    val address: String?,
    val categories: List<String> = emptyList(),
    val startedAt: Instant,
    val endedAt: Instant?,
    val rating: Int?,
    val note: String,
) {
    init {
        require(restaurantName.isNotBlank()) { "restaurantName is required" }
        require(rating == null || rating in 1..5) { "rating must be null or between 1 and 5" }
        require(endedAt == null || !endedAt.isBefore(startedAt)) { "endedAt cannot be before startedAt" }
    }
}
