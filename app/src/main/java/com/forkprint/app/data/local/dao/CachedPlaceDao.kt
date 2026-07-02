package com.forkprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.forkprint.app.data.local.entity.CachedPlaceEntity

@Dao
interface CachedPlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(places: List<CachedPlaceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(place: CachedPlaceEntity)

    @Query("SELECT * FROM cached_places WHERE googlePlaceId = :placeId LIMIT 1")
    suspend fun findByPlaceId(placeId: String): CachedPlaceEntity?

    @Query("""
        SELECT * FROM cached_places
        WHERE lower(name) LIKE '%' || lower(:query) || '%'
           OR lower(coalesce(address, '')) LIKE '%' || lower(:query) || '%'
           OR lower(categories) LIKE '%' || lower(:query) || '%'
        ORDER BY fetchedAt DESC
        LIMIT :limit
    """)
    suspend fun search(query: String, limit: Int = 20): List<CachedPlaceEntity>

    @Query("""
        SELECT * FROM cached_places
        WHERE latitude IS NOT NULL AND longitude IS NOT NULL
          AND latitude BETWEEN :minLatitude AND :maxLatitude
          AND longitude BETWEEN :minLongitude AND :maxLongitude
        ORDER BY fetchedAt DESC
        LIMIT :limit
    """)
    suspend fun nearby(
        minLatitude: Double,
        maxLatitude: Double,
        minLongitude: Double,
        maxLongitude: Double,
        limit: Int = 20,
    ): List<CachedPlaceEntity>
}
