package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.RestaurantMemory
import com.forkprint.app.domain.model.Visit

object BuildRestaurantMemory {
    fun from(restaurant: Restaurant, visits: List<Visit>): RestaurantMemory {
        val scopedVisits = visits.filter { it.restaurant.id == restaurant.id }
        val chronological = scopedVisits.sortedBy { it.startedAt }
        val ratings = scopedVisits.mapNotNull { it.rating }
        return RestaurantMemory(
            restaurant = restaurant,
            visitCount = scopedVisits.size,
            firstVisitAt = chronological.firstOrNull()?.startedAt,
            mostRecentVisitAt = chronological.lastOrNull()?.startedAt,
            averageRating = ratings.takeIf { it.isNotEmpty() }?.average(),
            notes = chronological.map { it.note.trim() }.filter { it.isNotBlank() },
            visits = scopedVisits.sortedByDescending { it.startedAt },
        )
    }
}
