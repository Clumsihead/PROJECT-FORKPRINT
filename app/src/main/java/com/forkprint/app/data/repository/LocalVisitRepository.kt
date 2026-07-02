package com.forkprint.app.data.repository

import com.forkprint.app.data.local.dao.RestaurantDao
import com.forkprint.app.data.local.dao.VisitDao
import com.forkprint.app.data.local.entity.RestaurantEntity
import com.forkprint.app.data.local.entity.VisitEntity
import com.forkprint.app.data.mapper.categoriesToString
import com.forkprint.app.data.mapper.toDomain
import com.forkprint.app.domain.model.LocalAnalytics
import com.forkprint.app.domain.model.RestaurantMemory
import com.forkprint.app.domain.model.VisitDraft
import com.forkprint.app.domain.model.VisitUpdate
import com.forkprint.app.domain.repository.VisitRepository
import com.forkprint.app.domain.usecase.BuildRestaurantMemory
import com.forkprint.app.domain.usecase.CalculateAnalytics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalVisitRepository @Inject constructor(
    private val restaurantDao: RestaurantDao,
    private val visitDao: VisitDao,
) : VisitRepository {
    override fun observeTimeline() = visitDao.observeTimeline().map { rows -> rows.map { it.toDomain() } }

    override fun observeVisit(visitId: String) = visitDao.observeVisit(visitId).map { it?.toDomain() }

    override fun observeRestaurantMemory(restaurantId: String): Flow<RestaurantMemory?> = combine(
        restaurantDao.observeById(restaurantId),
        visitDao.observeVisitsForRestaurant(restaurantId),
    ) { restaurant, visits ->
        restaurant?.let { BuildRestaurantMemory.from(it.toDomain(), visits.map { row -> row.toDomain() }) }
    }

    override fun searchVisits(query: String) = if (query.isBlank()) {
        observeTimeline()
    } else {
        visitDao.search(query).map { rows -> rows.map { it.toDomain() } }
    }

    override fun observeAnalytics(): Flow<LocalAnalytics> = observeTimeline().map(CalculateAnalytics::from)

    override suspend fun addVisit(draft: VisitDraft): String {
        require(draft.restaurantName.isNotBlank()) { "restaurantName is required" }
        require(draft.rating == null || draft.rating in 1..5) { "rating must be null or between 1 and 5" }
        val now = Instant.now()
        val restaurant = findOrCreateRestaurant(draft, now)
        val visitId = UUID.randomUUID().toString()
        visitDao.insert(
            VisitEntity(
                id = visitId,
                restaurantId = restaurant.id,
                startedAt = draft.startedAt,
                endedAt = draft.endedAt,
                rating = draft.rating,
                note = draft.note.trim(),
                source = draft.source,
                createdAt = now,
                updatedAt = now,
            )
        )
        return visitId
    }

    override suspend fun updateVisit(visitId: String, update: VisitUpdate) {
        val existing = visitDao.findById(visitId) ?: return
        val now = Instant.now()
        val restaurant = restaurantDao.findById(existing.restaurantId)?.let { current ->
            val categories = categoriesToString(update.categories)
            restaurantDao.updateRestaurantInfo(
                restaurantId = current.id,
                name = update.restaurantName.trim(),
                address = update.address?.trim()?.takeIf { it.isNotBlank() },
                categories = categories,
                updatedAt = now,
            )
            current.copy(
                name = update.restaurantName.trim(),
                address = update.address?.trim()?.takeIf { it.isNotBlank() },
                categories = categories,
                updatedAt = now,
            )
        } ?: findOrCreateRestaurant(
            VisitDraft(
                restaurantName = update.restaurantName,
                address = update.address,
                categories = update.categories,
            ),
            now,
        )
        visitDao.updateVisit(
            visitId = visitId,
            restaurantId = restaurant.id,
            startedAt = update.startedAt,
            endedAt = update.endedAt,
            rating = update.rating,
            note = update.note.trim(),
            updatedAt = now,
        )
    }

    override suspend fun updateVisitRating(visitId: String, rating: Int?) {
        require(rating == null || rating in 1..5) { "rating must be null or between 1 and 5" }
        visitDao.updateRating(visitId, rating, Instant.now())
    }

    override suspend fun updateVisitNote(visitId: String, note: String) = visitDao.updateNote(visitId, note.trim(), Instant.now())

    override suspend fun deleteVisit(visitId: String) = visitDao.deleteVisit(visitId)

    override suspend fun deleteEverything() {
        visitDao.deleteAll()
        restaurantDao.deleteAll()
    }

    private suspend fun findOrCreateRestaurant(draft: VisitDraft, now: Instant): RestaurantEntity {
        draft.googlePlaceId?.let { restaurantDao.findByGooglePlaceId(it) }?.let { return maybeEnrichRestaurant(it, draft, now) }
        restaurantDao.findByNameAndAddress(draft.restaurantName.trim(), draft.address)?.let { return maybeEnrichRestaurant(it, draft, now) }
        val entity = RestaurantEntity(
            id = UUID.randomUUID().toString(),
            name = draft.restaurantName.trim(),
            address = draft.address?.trim()?.takeIf { it.isNotBlank() },
            latitude = draft.latitude,
            longitude = draft.longitude,
            googlePlaceId = draft.googlePlaceId,
            categories = categoriesToString(draft.categories),
            createdAt = now,
            updatedAt = now,
        )
        restaurantDao.upsert(entity)
        return entity
    }

    private suspend fun maybeEnrichRestaurant(entity: RestaurantEntity, draft: VisitDraft, now: Instant): RestaurantEntity {
        val categories = categoriesToString(draft.categories).takeIf { it.isNotBlank() } ?: entity.categories
        val updated = entity.copy(
            address = entity.address ?: draft.address?.trim()?.takeIf { it.isNotBlank() },
            latitude = entity.latitude ?: draft.latitude,
            longitude = entity.longitude ?: draft.longitude,
            categories = categories,
            updatedAt = now,
        )
        if (updated != entity) restaurantDao.upsert(updated)
        return updated
    }
}
