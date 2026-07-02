package com.forkprint.app.domain.model

import java.time.Instant

data class VisitDraft(
    val restaurantName: String,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val googlePlaceId: String? = null,
    val categories: List<String> = emptyList(),
    val startedAt: Instant = Instant.now(),
    val endedAt: Instant? = null,
    val rating: Int? = null,
    val note: String = "",
    val source: VisitSource = VisitSource.Manual,
)
