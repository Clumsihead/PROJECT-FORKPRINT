package com.forkprint.app.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?,
    val googlePlaceId: String?,
    val categories: List<String> = emptyList(),
)
