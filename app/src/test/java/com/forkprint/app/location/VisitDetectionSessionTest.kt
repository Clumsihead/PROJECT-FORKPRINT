package com.forkprint.app.location

import com.forkprint.app.places.PlaceCandidate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.Instant

class VisitDetectionSessionTest {
    @Test
    fun emitsDetectedRestaurantOnlyAfterEnoughDwellTime() {
        val session = VisitDetectionSession()
        val place = place("google-ramen", "Ramen House")

        assertNull(session.record(PlaceObservation(place, distanceMeters = 22.0, observedAt = Instant.parse("2026-06-29T12:00:00Z"))))

        val detected = session.record(PlaceObservation(place, distanceMeters = 20.0, observedAt = Instant.parse("2026-06-29T12:25:00Z")))

        assertEquals("Ramen House", detected?.restaurantName)
        assertEquals("google-ramen", detected?.googlePlaceId)
    }

    @Test
    fun doesNotEmitDuplicateDetectionForSamePlaceSession() {
        val session = VisitDetectionSession()
        val place = place("google-taco", "Taco Bar")

        session.record(PlaceObservation(place, distanceMeters = 18.0, observedAt = Instant.parse("2026-06-29T18:00:00Z")))
        val first = session.record(PlaceObservation(place, distanceMeters = 16.0, observedAt = Instant.parse("2026-06-29T18:30:00Z")))
        val duplicate = session.record(PlaceObservation(place, distanceMeters = 12.0, observedAt = Instant.parse("2026-06-29T19:00:00Z")))

        assertEquals("Taco Bar", first?.restaurantName)
        assertNull(duplicate)
    }

    private fun place(id: String, name: String) = PlaceCandidate(
        name = name,
        address = "123 Main St",
        latitude = 40.0,
        longitude = -73.0,
        googlePlaceId = id,
        categories = listOf("restaurant"),
    )
}
