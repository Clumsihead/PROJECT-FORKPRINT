package com.forkprint.app.data.mapper

import com.forkprint.app.data.local.entity.RestaurantEntity
import com.forkprint.app.data.local.entity.VisitEntity
import com.forkprint.app.data.local.model.VisitWithRestaurant
import com.forkprint.app.domain.model.Restaurant
import com.forkprint.app.domain.model.Visit
import java.time.Instant

private fun String.toCategoryList() = split('|').map { it.trim() }.filter { it.isNotBlank() }
private fun List<String>.toCategoryString() = map { it.trim() }.filter { it.isNotBlank() }.distinct().joinToString("|")

fun RestaurantEntity.toDomain() = Restaurant(id, name, address, latitude, longitude, googlePlaceId, categories.toCategoryList())
fun VisitWithRestaurant.toDomain() = Visit(visit.id, restaurant.toDomain(), visit.startedAt, visit.endedAt, visit.rating, visit.note, visit.source)

fun Restaurant.toEntity(now: Instant) = RestaurantEntity(id, name, address, latitude, longitude, googlePlaceId, categories.toCategoryString(), now, now)
fun Visit.toEntity(now: Instant) = VisitEntity(id, restaurant.id, startedAt, endedAt, rating, note, source, now, now)
fun categoriesToString(categories: List<String>) = categories.toCategoryString()
