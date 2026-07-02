package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.model.JourneyCluster
import com.forkprint.app.domain.model.JourneyMap
import com.forkprint.app.domain.model.JourneyPlace
import com.forkprint.app.domain.model.Visit
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object BuildJourneyMap {
    fun from(visits: List<Visit>, clusterDistanceMeters: Double = 175.0): JourneyMap {
        val groupedByRestaurant = visits.groupBy { it.restaurant.id }
        val places = groupedByRestaurant.values.mapNotNull { restaurantVisits ->
            val restaurant = restaurantVisits.first().restaurant
            val latitude = restaurant.latitude ?: return@mapNotNull null
            val longitude = restaurant.longitude ?: return@mapNotNull null
            val sorted = restaurantVisits.sortedBy { it.startedAt }
            val ratings = restaurantVisits.mapNotNull { it.rating }
            JourneyPlace(
                restaurant = restaurant,
                latitude = latitude,
                longitude = longitude,
                visitCount = restaurantVisits.size,
                firstVisitAt = sorted.first().startedAt,
                mostRecentVisitAt = sorted.last().startedAt,
                averageRating = ratings.takeIf { it.isNotEmpty() }?.average(),
            )
        }.sortedWith(compareByDescending<JourneyPlace> { it.visitCount }.thenBy { it.restaurant.name })
        val mappedRestaurantIds = places.map { it.restaurant.id }.toSet()
        return JourneyMap(
            places = places,
            clusters = cluster(places, clusterDistanceMeters),
            totalMappedVisits = visits.count { it.restaurant.id in mappedRestaurantIds },
            unmappedVisitCount = visits.count { it.restaurant.id !in mappedRestaurantIds },
        )
    }

    private fun cluster(places: List<JourneyPlace>, clusterDistanceMeters: Double): List<JourneyCluster> {
        val clusters = mutableListOf<MutableList<JourneyPlace>>()
        places.forEach { place ->
            val existing = clusters.firstOrNull { cluster ->
                cluster.any { distanceMeters(it.latitude, it.longitude, place.latitude, place.longitude) <= clusterDistanceMeters }
            }
            if (existing == null) clusters += mutableListOf(place) else existing += place
        }
        return clusters.map { clusterPlaces ->
            JourneyCluster(
                latitude = clusterPlaces.map { it.latitude }.average(),
                longitude = clusterPlaces.map { it.longitude }.average(),
                places = clusterPlaces.sortedWith(compareByDescending<JourneyPlace> { it.visitCount }.thenBy { it.restaurant.name }),
            )
        }.sortedByDescending { it.visitCount }
    }

    private fun distanceMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadiusMeters = 6_371_000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2)
        return earthRadiusMeters * 2 * atan2(sqrt(a), sqrt(1 - a))
    }
}
