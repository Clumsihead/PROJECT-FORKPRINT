package com.forkprint.app.di

import android.content.Context
import androidx.room.Room
import com.forkprint.app.ai.AiMemoryProvider
import com.forkprint.app.ai.NoOpAiMemoryProvider
import com.forkprint.app.data.local.ForkprintDatabase
import com.forkprint.app.data.local.ForkprintMigrations
import com.forkprint.app.data.repository.LocalVisitRepository
import com.forkprint.app.domain.repository.VisitRepository
import com.forkprint.app.location.AndroidLocationUpdateProvider
import com.forkprint.app.location.AndroidRestaurantVisitDetector
import com.forkprint.app.location.LocationUpdateProvider
import com.forkprint.app.location.RestaurantVisitDetector
import com.forkprint.app.places.GooglePlacesProvider
import com.forkprint.app.places.PlacesProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton abstract fun bindVisitRepository(repository: LocalVisitRepository): VisitRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ForkprintDatabase = Room.databaseBuilder(
        context,
        ForkprintDatabase::class.java,
        "forkprint.db",
    ).addMigrations(ForkprintMigrations.MIGRATION_1_2, ForkprintMigrations.MIGRATION_2_3).build()

    @Provides fun provideRestaurantDao(database: ForkprintDatabase) = database.restaurantDao()
    @Provides fun provideVisitDao(database: ForkprintDatabase) = database.visitDao()
    @Provides fun provideCachedPlaceDao(database: ForkprintDatabase) = database.cachedPlaceDao()
    @Provides @Singleton fun provideAiMemoryProvider(): AiMemoryProvider = NoOpAiMemoryProvider()
    @Provides @Singleton fun providePlacesProvider(provider: GooglePlacesProvider): PlacesProvider = provider
    @Provides @Singleton fun provideLocationUpdateProvider(provider: AndroidLocationUpdateProvider): LocationUpdateProvider = provider
    @Provides @Singleton fun provideVisitDetector(detector: AndroidRestaurantVisitDetector): RestaurantVisitDetector = detector
}
