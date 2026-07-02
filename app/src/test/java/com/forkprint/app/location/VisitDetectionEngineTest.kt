package com.forkprint.app.location

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VisitDetectionEngineTest {
    @Test
    fun marksRestaurantCandidateAsVisitWhenDwellTimeIsLongEnough() {
        val result = VisitDetectionEngine.isLikelyVisit(
            candidate = RestaurantCandidateSignal(
                distanceMeters = 24.0,
                dwellMinutes = 42,
                confidence = 0.82,
            ),
        )

        assertTrue(result)
    }

    @Test
    fun rejectsShortStopsEvenWhenTheyAreNearARestaurant() {
        val result = VisitDetectionEngine.isLikelyVisit(
            candidate = RestaurantCandidateSignal(
                distanceMeters = 10.0,
                dwellMinutes = 4,
                confidence = 0.95,
            ),
        )

        assertFalse(result)
    }
}
