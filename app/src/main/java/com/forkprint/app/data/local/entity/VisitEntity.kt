package com.forkprint.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.forkprint.app.domain.model.VisitSource
import java.time.Instant

@Entity(
    tableName = "visits",
    foreignKeys = [ForeignKey(entity = RestaurantEntity::class, parentColumns = ["id"], childColumns = ["restaurantId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("restaurantId"), Index("startedAt")]
)
data class VisitEntity(
    @PrimaryKey val id: String,
    val restaurantId: String,
    val startedAt: Instant,
    val endedAt: Instant?,
    val rating: Int?,
    val note: String,
    val source: VisitSource,
    val createdAt: Instant,
    val updatedAt: Instant,
)
