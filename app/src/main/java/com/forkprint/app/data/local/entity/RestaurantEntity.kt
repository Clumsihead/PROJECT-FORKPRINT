package com.forkprint.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "restaurants", indices = [Index(value = ["googlePlaceId"], unique = true)])
data class RestaurantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val googlePlaceId: String?,
    val categories: String = "",
    val createdAt: Instant,
    val updatedAt: Instant,
)
