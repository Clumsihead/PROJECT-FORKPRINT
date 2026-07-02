package com.forkprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.forkprint.app.data.local.entity.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: RestaurantEntity)

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId LIMIT 1")
    fun observeById(restaurantId: String): Flow<RestaurantEntity?>

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId LIMIT 1")
    suspend fun findById(restaurantId: String): RestaurantEntity?

    @Query("SELECT * FROM restaurants WHERE googlePlaceId = :googlePlaceId LIMIT 1")
    suspend fun findByGooglePlaceId(googlePlaceId: String): RestaurantEntity?

    @Query("SELECT * FROM restaurants WHERE lower(name) = lower(:name) AND coalesce(address, '') = coalesce(:address, '') LIMIT 1")
    suspend fun findByNameAndAddress(name: String, address: String?): RestaurantEntity?

    @Query("UPDATE restaurants SET name = :name, address = :address, categories = :categories, updatedAt = :updatedAt WHERE id = :restaurantId")
    suspend fun updateRestaurantInfo(restaurantId: String, name: String, address: String?, categories: String, updatedAt: java.time.Instant)

    @Query("DELETE FROM restaurants")
    suspend fun deleteAll()
}
