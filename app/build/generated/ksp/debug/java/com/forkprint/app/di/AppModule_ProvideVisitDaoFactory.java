package com.forkprint.app.di;

import com.forkprint.app.data.local.ForkprintDatabase;
import com.forkprint.app.data.local.dao.VisitDao;
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
public final class AppModule_ProvideVisitDaoFactory implements Factory<VisitDao> {
  private final Provider<ForkprintDatabase> databaseProvider;

  public AppModule_ProvideVisitDaoFactory(Provider<ForkprintDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VisitDao get() {
    return provideVisitDao(databaseProvider.get());
  }

  public static AppModule_ProvideVisitDaoFactory create(
      Provider<ForkprintDatabase> databaseProvider) {
    return new AppModule_ProvideVisitDaoFactory(databaseProvider);
  }

  public static VisitDao provideVisitDao(ForkprintDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVisitDao(database));
  }
}
