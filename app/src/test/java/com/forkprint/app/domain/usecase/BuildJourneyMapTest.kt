package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.model.VisitSource
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class BuildJourneyMapTest {
    @Test
    fun keepsOnlyVisitedRestaurantsWithCoordinatesAndSummarizesCounts() {
        val ramen = restaurant("r1", "Ramen House", 40.0000, -73.0000)
        val tacos = restaurant("r2", "Taco Garden", 40.0010, -73.0010)
        val unknown = restaurant("r3", "No Map Cafe", null, null)
        val visits = listOf(
            visit("v1", ramen, "2026-01-01T18:00:00Z", 5),
            visit("v2", ramen, "2026-02-01T18:00:00Z", 3),
            visit("v3", tacos, "2026-03-01T18:00:00Z", null),
            visit("v4", unknown, "2026-04-01T18:00:00Z", 4),
        )

        val journey = BuildJourneyMap.from(visits)

        assertEquals(2, journey.places.size)
        assertEquals("Ramen House", journey.places.first().restaurant.name)
        assertEquals(2, journey.places.first().visitCount)
        assertEquals(4.0, journey.places.first().averageRating!!, 0.001)
        assertEquals(3, journey.totalMappedVisits)
        assertEquals(1, journey.unmappedVisitCount)
    }

    @Test
    fun clustersNearbyRestaurantMemoriesWithoutMergingTheirIdentity() {
        val visits = listOf(
            visit("v1", restaurant("r1", "One", 40.0000, -73.0000), "2026-01-01T18:00:00Z", null),
            visit("v2", restaurant("r2", "Two", 40.0008, -73.0008), "2026-01-02T18:00:00Z", null),
            visit("v3", restaurant("r3", "Far", 41.0000, -74.0000), "2026-01-03T18:00:00Z", null),
        )

        val journey = BuildJourneyMap.from(visits, clusterDistanceMeters = 150.0)

        assertEquals(2, journey.clusters.size)
        assertEquals(2, journey.clusters.first().places.size)
        assertEquals(2, journey.clusters.first().visitCount)
        assertEquals(1, journey.clusters.last().places.size)
    }

    private fun restaurant(id: String, name: String, latitude: Double?, longitude: Double?) = Restaurant(
        id = id,
        name = name,
        address = null,
        latitude = latitude,
        longitude = longitude,
        googlePlaceId = null,
        categories = emptyList(),
    )

    private fun visit(id: String, restaurant: Restaurant, startedAt: String, rating: Int?) = Visit(
        id = id,
        restaurant = restaurant,
        startedAt = Instant.parse(startedAt),
        endedAt = null,
        rating = rating,
        note = "",
        source = VisitSource.Manual,
    )
}
