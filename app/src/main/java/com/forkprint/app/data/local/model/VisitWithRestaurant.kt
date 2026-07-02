package com.forkprint.app.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.forkprint.app.data.local.entity.RestaurantEntity
import com.forkprint.app.data.local.entity.VisitEntity

data class VisitWithRestaurant(
    @Embedded val visit: VisitEntity,
    @Relation(parentColumn = "restaurantId", entityColumn = "id") val restaurant: RestaurantEntity,
)
