package com.forkprint.app.data.repository;

import com.forkprint.app.data.local.dao.RestaurantDao;
import com.forkprint.app.data.local.dao.VisitDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class LocalVisitRepository_Factory implements Factory<LocalVisitRepository> {
  private final Provider<RestaurantDao> restaurantDaoProvider;

  private final Provider<VisitDao> visitDaoProvider;

  public LocalVisitRepository_Factory(Provider<RestaurantDao> restaurantDaoProvider,
      Provider<VisitDao> visitDaoProvider) {
    this.restaurantDaoProvider = restaurantDaoProvider;
    this.visitDaoProvider = visitDaoProvider;
  }

  @Override
  public LocalVisitRepository get() {
    return newInstance(restaurantDaoProvider.get(), visitDaoProvider.get());
  }

  public static LocalVisitRepository_Factory create(Provider<RestaurantDao> restaurantDaoProvider,
      Provider<VisitDao> visitDaoProvider) {
    return new LocalVisitRepository_Factory(restaurantDaoProvider, visitDaoProvider);
  }

  public static LocalVisitRepository newInstance(RestaurantDao restaurantDao, VisitDao visitDao) {
    return new LocalVisitRepository(restaurantDao, visitDao);
  }
}
