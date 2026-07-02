package com.forkprint.app.domain.model

data class LocalAnalytics(
    val totalVisits: Int,
    val uniqueRestaurants: Int,
    val averageRating: Double?,
    val topRestaurantName: String?,
)
