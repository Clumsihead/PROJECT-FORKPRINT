package com.forkprint.app.location

import com.forkprint.app.domain.model.VisitDraft
import com.forkprint.app.domain.model.VisitSource
import com.forkprint.app.places.PlaceCandidate
import java.time.Instant

object VisitDraftFactory {
    fun fromDetectedCandidate(place: PlaceCandidate, detectedAt: Instant): VisitDraft = VisitDraft(
        restaurantName = place.name,
        address = place.address,
        latitude = place.latitude,
        longitude = place.longitude,
        googlePlaceId = place.googlePlaceId,
        categories = place.categories,
        startedAt = detectedAt,
        source = VisitSource.Detected,
    )
}
