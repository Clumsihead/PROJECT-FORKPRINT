package com.forkprint.app.domain.usecase

import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.model.VisitSource
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class BuildRestaurantMemoryTest {
    @Test
    fun summarizesUserHistoryAtRestaurantFromVisits() {
        val restaurant = Restaurant(
            id = "restaurant-1",
            name = "Little Ramen",
            address = "12 Quiet St",
            latitude = null,
            longitude = null,
            googlePlaceId = "google-1",
            categories = listOf("restaurant", "ramen"),
        )
        val visits = listOf(
            Visit("visit-1", restaurant, Instant.parse("2026-01-10T18:00:00Z"), Instant.parse("2026-01-10T19:10:00Z"), 5, "Snowy night noodles", VisitSource.Manual),
            Visit("visit-2", restaurant, Instant.parse("2026-03-12T12:15:00Z"), null, 3, "Quick lunch", VisitSource.Detected),
            Visit("visit-3", restaurant, Instant.parse("2026-05-01T20:00:00Z"), null, null, "", VisitSource.Detected),
        )

        val memory = BuildRestaurantMemory.from(restaurant, visits)

        assertEquals(3, memory.visitCount)
        assertEquals(Instant.parse("2026-01-10T18:00:00Z"), memory.firstVisitAt)
        assertEquals(Instant.parse("2026-05-01T20:00:00Z"), memory.mostRecentVisitAt)
        assertEquals(4.0, memory.averageRating!!, 0.001)
        assertEquals(listOf("Snowy night noodles", "Quick lunch"), memory.notes)
        assertEquals(listOf("visit-3", "visit-2", "visit-1"), memory.visits.map { it.id })
    }
}
