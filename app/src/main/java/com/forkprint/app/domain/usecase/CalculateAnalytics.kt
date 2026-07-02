package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.model.LocalAnalytics
import com.forkprint.app.domain.model.Visit

object CalculateAnalytics {
    fun from(visits: List<Visit>): LocalAnalytics {
        val average = visits.mapNotNull { it.rating }.takeIf { it.isNotEmpty() }?.average()
        val topRestaurant = visits
            .groupBy { it.restaurant.name }
            .maxByOrNull { (_, restaurantVisits) -> restaurantVisits.size }
            ?.key
        return LocalAnalytics(
            totalVisits = visits.size,
            uniqueRestaurants = visits.map { it.restaurant.id }.distinct().size,
            averageRating = average,
            topRestaurantName = topRestaurant,
        )
    }
}
