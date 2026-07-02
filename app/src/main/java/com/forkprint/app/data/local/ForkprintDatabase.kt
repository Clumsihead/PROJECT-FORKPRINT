package com.forkprint.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.forkprint.app.data.local.dao.RestaurantDao
import com.forkprint.app.data.local.dao.VisitDao
import com.forkprint.app.data.local.dao.CachedPlaceDao
import com.forkprint.app.data.local.entity.CachedPlaceEntity
import com.forkprint.app.data.local.entity.RestaurantEntity
import com.forkprint.app.data.local.entity.VisitEntity

@Database(entities = [RestaurantEntity::class, VisitEntity::class, CachedPlaceEntity::class], version = 3, exportSchema = true)
@TypeConverters(ForkprintTypeConverters::class)
abstract class ForkprintDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    abstract fun visitDao(): VisitDao
    abstract fun cachedPlaceDao(): CachedPlaceDao
}
