package com.forkprint.app.di;

import com.forkprint.app.data.local.ForkprintDatabase;
import com.forkprint.app.data.local.dao.CachedPlaceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AppModule_ProvideCachedPlaceDaoFactory implements Factory<CachedPlaceDao> {
  private final Provider<ForkprintDatabase> databaseProvider;

  public AppModule_ProvideCachedPlaceDaoFactory(Provider<ForkprintDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CachedPlaceDao get() {
    return provideCachedPlaceDao(databaseProvider.get());
  }

  public static AppModule_ProvideCachedPlaceDaoFactory create(
      Provider<ForkprintDatabase> databaseProvider) {
    return new AppModule_ProvideCachedPlaceDaoFactory(databaseProvider);
  }

  public static CachedPlaceDao provideCachedPlaceDao(ForkprintDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCachedPlaceDao(database));
  }
}
