package com.forkprint.app.places

import java.time.Instant

data class PlaceCandidate(
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val googlePlaceId: String?,
    val categories: List<String> = emptyList(),
    val fetchedAt: Instant? = null,
)

interface PlacesProvider {
    suspend fun searchRestaurants(query: String): List<PlaceCandidate>
    suspend fun nearbyRestaurants(latitude: Double, longitude: Double): List<PlaceCandidate>
    suspend fun placeDetails(placeId: String): PlaceCandidate?
}

class OfflinePlacesProvider : PlacesProvider {
    override suspend fun searchRestaurants(query: String): List<PlaceCandidate> = emptyList()
    override suspend fun nearbyRestaurants(latitude: Double, longitude: Double): List<PlaceCandidate> = emptyList()
    override suspend fun placeDetails(placeId: String): PlaceCandidate? = null
}
