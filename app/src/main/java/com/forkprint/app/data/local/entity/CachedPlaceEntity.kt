package com.forkprint.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "cached_places",
    indices = [Index("name"), Index("latitude", "longitude")],
)
data class CachedPlaceEntity(
    @PrimaryKey val googlePlaceId: String,
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val categories: String,
    val fetchedAt: Instant,
)
