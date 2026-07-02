package com.forkprint.app.location

import com.forkprint.app.domain.model.VisitDraft
import kotlinx.coroutines.flow.Flow

interface RestaurantVisitDetector {
    fun detectedVisits(): Flow<VisitDraft>
}

class DisabledRestaurantVisitDetector : RestaurantVisitDetector {
    override fun detectedVisits(): Flow<VisitDraft> = kotlinx.coroutines.flow.emptyFlow()
}
