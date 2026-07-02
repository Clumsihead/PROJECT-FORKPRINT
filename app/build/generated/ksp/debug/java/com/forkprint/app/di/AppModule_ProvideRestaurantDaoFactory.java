package com.forkprint.app.di;

import com.forkprint.app.data.local.ForkprintDatabase;
import com.forkprint.app.data.local.dao.RestaurantDao;
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
public final class AppModule_ProvideRestaurantDaoFactory implements Factory<RestaurantDao> {
  private final Provider<ForkprintDatabase> databaseProvider;

  public AppModule_ProvideRestaurantDaoFactory(Provider<ForkprintDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RestaurantDao get() {
    return provideRestaurantDao(databaseProvider.get());
  }

  public static AppModule_ProvideRestaurantDaoFactory create(
      Provider<ForkprintDatabase> databaseProvider) {
    return new AppModule_ProvideRestaurantDaoFactory(databaseProvider);
  }

  public static RestaurantDao provideRestaurantDao(ForkprintDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideRestaurantDao(database));
  }
}
