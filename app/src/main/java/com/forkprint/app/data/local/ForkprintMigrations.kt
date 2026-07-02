package com.forkprint.app.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object ForkprintMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS cached_places (
                    googlePlaceId TEXT NOT NULL PRIMARY KEY,
                    name TEXT NOT NULL,
                    address TEXT,
                    latitude REAL,
                    longitude REAL,
                    categories TEXT NOT NULL,
                    fetchedAt INTEGER NOT NULL
                )
                """.trimIndent(),
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_cached_places_name ON cached_places(name)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_cached_places_latitude_longitude ON cached_places(latitude, longitude)")
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE restaurants ADD COLUMN categories TEXT NOT NULL DEFAULT ''")
        }
    }
}
