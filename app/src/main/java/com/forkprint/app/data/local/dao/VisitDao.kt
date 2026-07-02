package com.forkprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.forkprint.app.data.local.entity.VisitEntity
import com.forkprint.app.data.local.model.VisitWithRestaurant
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitDao {
    @Transaction
    @Query("SELECT * FROM visits ORDER BY startedAt DESC")
    fun observeTimeline(): Flow<List<VisitWithRestaurant>>

    @Transaction
    @Query("SELECT * FROM visits WHERE id = :visitId LIMIT 1")
    fun observeVisit(visitId: String): Flow<VisitWithRestaurant?>

    @Transaction
    @Query("SELECT * FROM visits WHERE restaurantId = :restaurantId ORDER BY startedAt DESC")
    fun observeVisitsForRestaurant(restaurantId: String): Flow<List<VisitWithRestaurant>>

    @Transaction
    @Query("""
        SELECT visits.* FROM visits
        INNER JOIN restaurants ON restaurants.id = visits.restaurantId
        WHERE lower(restaurants.name) LIKE '%' || lower(:query) || '%'
           OR lower(coalesce(restaurants.address, '')) LIKE '%' || lower(:query) || '%'
           OR lower(restaurants.categories) LIKE '%' || lower(:query) || '%'
           OR lower(visits.note) LIKE '%' || lower(:query) || '%'
        ORDER BY visits.startedAt DESC
    """)
    fun search(query: String): Flow<List<VisitWithRestaurant>>

    @Insert
    suspend fun insert(entity: VisitEntity)

    @Update
    suspend fun update(entity: VisitEntity)

    @Query("SELECT * FROM visits WHERE id = :visitId LIMIT 1")
    suspend fun findById(visitId: String): VisitEntity?

    @Query("UPDATE visits SET restaurantId = :restaurantId, startedAt = :startedAt, endedAt = :endedAt, rating = :rating, note = :note, updatedAt = :updatedAt WHERE id = :visitId")
    suspend fun updateVisit(visitId: String, restaurantId: String, startedAt: java.time.Instant, endedAt: java.time.Instant?, rating: Int?, note: String, updatedAt: java.time.Instant)

    @Query("UPDATE visits SET rating = :rating, updatedAt = :updatedAt WHERE id = :visitId")
    suspend fun updateRating(visitId: String, rating: Int?, updatedAt: java.time.Instant)

    @Query("UPDATE visits SET note = :note, updatedAt = :updatedAt WHERE id = :visitId")
    suspend fun updateNote(visitId: String, note: String, updatedAt: java.time.Instant)

    @Query("DELETE FROM visits WHERE id = :visitId")
    suspend fun deleteVisit(visitId: String)

    @Query("DELETE FROM visits")
    suspend fun deleteAll()
}
