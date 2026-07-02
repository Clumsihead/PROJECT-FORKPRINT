package com.forkprint.app.places

import com.forkprint.app.data.local.entity.CachedPlaceEntity
import java.time.Instant

fun CachedPlaceEntity.toPlaceCandidate() = PlaceCandidate(
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    googlePlaceId = googlePlaceId,
    categories = categories.split('|').filter { it.isNotBlank() },
    fetchedAt = fetchedAt,
)

fun PlaceCandidate.toCachedPlaceEntity(now: Instant = Instant.now()): CachedPlaceEntity? {
    val placeId = googlePlaceId ?: return null
    return CachedPlaceEntity(
        googlePlaceId = placeId,
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude,
        categories = categories.joinToString("|"),
        fetchedAt = fetchedAt ?: now,
    )
}
