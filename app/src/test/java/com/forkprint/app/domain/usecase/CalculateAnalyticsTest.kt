package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.model.VisitSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Instant

class CalculateAnalyticsTest {
    @Test
    fun calculatesVisitCountsUniqueRestaurantsAndAverageRating() {
        val ramen = restaurant("r1", "Ramen House")
        val taco = restaurant("r2", "Taco Bar")
        val visits = listOf(
            visit("v1", ramen, 5),
            visit("v2", ramen, 3),
            visit("v3", taco, null),
        )

        val analytics = CalculateAnalytics.from(visits)

        assertEquals(3, analytics.totalVisits)
        assertEquals(2, analytics.uniqueRestaurants)
        assertEquals(4.0, analytics.averageRating!!, 0.001)
        assertEquals("Ramen House", analytics.topRestaurantName)
    }

    @Test
    fun returnsNullAverageAndTopRestaurantWhenNoVisitsExist() {
        val analytics = CalculateAnalytics.from(emptyList())

        assertEquals(0, analytics.totalVisits)
        assertEquals(0, analytics.uniqueRestaurants)
        assertNull(analytics.averageRating)
        assertNull(analytics.topRestaurantName)
    }

    private fun restaurant(id: String, name: String) = Restaurant(id, name, null, null, null, null)

    private fun visit(id: String, restaurant: Restaurant, rating: Int?) = Visit(
        id = id,
        restaurant = restaurant,
        startedAt = Instant.EPOCH,
        endedAt = null,
        rating = rating,
        note = "",
        source = VisitSource.Manual,
    )
}
