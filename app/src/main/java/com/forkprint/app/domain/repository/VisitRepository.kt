package com.forkprint.app.domain.repository

import com.forkprint.app.domain.model.LocalAnalytics
import com.forkprint.app.domain.model.RestaurantMemory
import com.forkprint.app.domain.model.Visit
import com.forkprint.app.domain.model.VisitDraft
import com.forkprint.app.domain.model.VisitUpdate
import kotlinx.coroutines.flow.Flow

interface VisitRepository {
    fun observeTimeline(): Flow<List<Visit>>
    fun observeVisit(visitId: String): Flow<Visit?>
    fun observeRestaurantMemory(restaurantId: String): Flow<RestaurantMemory?>
    fun searchVisits(query: String): Flow<List<Visit>>
    fun observeAnalytics(): Flow<LocalAnalytics>
    suspend fun addVisit(draft: VisitDraft): String
    suspend fun updateVisit(visitId: String, update: VisitUpdate)
    suspend fun updateVisitRating(visitId: String, rating: Int?)
    suspend fun updateVisitNote(visitId: String, note: String)
    suspend fun deleteVisit(visitId: String)
    suspend fun deleteEverything()
}
